package com.test.test.presentation.movies

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.test.domain.repository.MoviesRepository
import com.test.test.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListingsViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    var state by mutableStateOf(MovieListingsState())

    private var searchJob: Job? = null

    init {
        getMovieListings()
    }

    fun onEvent(event: MovieListingsEvent){
        when(event){
            is MovieListingsEvent.OnSearchQueryChange -> {
              state = state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    // Поиск начнется через 300 миллисекунд после ввода последнего символа.
                    // Иначе мы бы выполняли лишную работу и искали что-то после каждого введённого символа.
                    // В данном небольшом проекте это не очень нужно, но т.к. по ТЗ нужна возможность расширения
                    // для большего проекта это было бы важно.
                    delay(300)
                    getMovieListings()
                }
            }
        }
    }

    private fun getMovieListings(query: String = state.searchQuery.lowercase()){
        viewModelScope.launch {
            repository.getMoviesListings(query)
                .collect{resolt->
                    when(resolt){
                        is Resource.Success ->{
                            resolt.data?.let { movies ->
                                state = state.copy(movies = movies)
                            }
                        }
                        is Resource.Error ->{
                            state = state.copy(errorMassage = resolt.massage)
                        }
                        is Resource.Loading ->{
                            state = state.copy(isLoading = resolt.isLoading)
                        }
                    }
                }
        }
    }
}
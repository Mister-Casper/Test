package com.test.test.presentation.movieCharacters

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.test.domain.repository.MoviesRepository
import com.test.test.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListingsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: MoviesRepository
) : ViewModel() {

    var state by mutableStateOf(CharacterListingsState())

    init {
        viewModelScope.launch {
            val episodeNum = savedStateHandle.get<Int>("episodeNum")!!
            state = state.copy(
                movieName = repository.getEpisodeName(episodeNum),
                episodeNum = episodeNum
            )
            getCharacterListings()
        }
    }

    private fun getCharacterListings(episodeNum: Int = state.episodeNum) {
        viewModelScope.launch {
            repository.getCharacters(episodeNum)
                .collect { resolt ->
                    when (resolt) {
                        is Resource.Success -> {
                            resolt.data?.let { characters ->
                                state = state.copy(characters = characters)
                            }
                        }
                        is Resource.Error -> {
                            state = state.copy(errorMassage = resolt.massage)
                        }
                        is Resource.Loading -> {
                            state = state.copy(isLoading = resolt.isLoading)
                        }
                    }
                }
        }
    }
}
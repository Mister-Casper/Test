package com.test.test.presentation.movies

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.test.test.R

@Composable
@Destination(start = true)
fun MovieListingsScree(
    navigation: DestinationsNavigator,
    viewModel: MovieListingsViewModel = hiltViewModel()
) {
    val state = viewModel.state
    Column(Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = { viewModel.onEvent(MovieListingsEvent.OnSearchQueryChange(it)) },
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            placeholder = {
                Text(text = stringResource(id = R.string.search))
            },
            maxLines = 1,
            singleLine = true
        )

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.movies.size) { i ->
                val movie = state.movies[i]
                MovieItem(movie = movie, modifier = Modifier.fillMaxWidth())
                if (i < state.movies.size) {
                    Divider(modifier = Modifier.padding(horizontal = 12.dp))
                }
            }
        }
    }
}
package com.test.test.presentation.movies

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.test.test.R
import com.test.test.presentation.destinations.CharacterListingsScreenDestination

@Composable
@Destination(start = true)
fun MovieListingsScreen(
    navigation: DestinationsNavigator,
    viewModel: MovieListingsViewModel = hiltViewModel()
) {
    val state = viewModel.state

    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
    if (state.errorMassage != null) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = state.errorMassage.getText(LocalContext.current),
                fontSize = 24.sp,
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(
                    Alignment.Center
                )
            )
        }
    }

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
                MovieItem(
                    movie = movie,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            navigation.navigate(CharacterListingsScreenDestination(movie.episode))
                        })
                if (i < state.movies.size) {
                    Divider(modifier = Modifier.padding(horizontal = 12.dp))
                }
            }
        }
    }
}
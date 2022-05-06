package com.test.test.presentation.movieCharacters

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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

@Composable
@Destination("CharacterListings")
fun CharacterListingsScreen(
    episodeNum: Int,
    navigation: DestinationsNavigator,
    viewModel: CharacterListingsViewModel = hiltViewModel()
) {
    val state = viewModel.state

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = state.movieName)
                },
                navigationIcon = {
                    IconButton(onClick = { navigation.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, "")
                    }
                },
                backgroundColor = Color.Blue,
                contentColor = Color.White,
                elevation = 12.dp
            )
        }, content = {
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
            if (state.errorMassage != null) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = state.errorMassage,
                        fontSize = 24.sp,
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(
                            Alignment.Center
                        )
                    )
                }
            } else
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.characters.size) { i ->
                        val character = state.characters[i]
                        CharacterItem(
                            character = character, modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                        if (i < state.characters.size) {
                            Divider(modifier = Modifier.padding(horizontal = 12.dp))
                        }
                    }
                }
        })
}
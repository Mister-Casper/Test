package com.test.test.presentation.movies

sealed class MovieListingsEvent {
    data class OnSearchQueryChange(val query:String):MovieListingsEvent()
}
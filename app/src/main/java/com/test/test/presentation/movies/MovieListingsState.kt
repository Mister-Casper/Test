package com.test.test.presentation.movies

import com.test.test.domain.model.MovieListing
import com.test.test.util.StringResource
import java.util.*

data class MovieListingsState(
    val movies: List<MovieListing> = Collections.emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val errorMassage: StringResource?= null
)
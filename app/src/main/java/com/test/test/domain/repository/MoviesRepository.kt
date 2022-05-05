package com.test.test.domain.repository

import com.test.test.domain.model.MovieListing
import com.test.test.util.Resource
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getMoviesListings(
        query:String
    ): Flow<Resource<List<MovieListing>>>
}
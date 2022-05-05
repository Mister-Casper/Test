package com.test.test.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET

interface MoviesApi {

    @GET("films")
    suspend fun getMovies() : ResponseBody

    companion object {
        const val BASE_URL = "https://swapi.dev/api/"
    }
}
package com.test.test.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("films")
    suspend fun getMovies() : ResponseBody

    @GET("people/{id}")
    suspend fun getCharacter(@Path("id")id:Int) : ResponseBody

    companion object {
        const val BASE_URL = "https://swapi.dev/api/"
    }
}
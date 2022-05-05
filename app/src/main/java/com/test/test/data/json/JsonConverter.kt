package com.test.test.data.json

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.test.test.data.remote.MovieModel
import com.test.test.presentation.movies.MovieListingsViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class JsonConverter @Inject constructor() {

    private val gson = Gson()

    fun convertMoviesListings(string: String): List<MovieModel> {
        val type: Type = object : TypeToken<ArrayList<MovieModel>>() {}.type
        val jsonObj = JSONObject(string)
        val gamesObj: JSONArray = jsonObj.getJSONArray("results")
        val list: List<MovieModel> = gson.fromJson(gamesObj.toString(), type)
        return list
    }
}
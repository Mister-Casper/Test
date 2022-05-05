package com.test.test.data.remote

import com.google.gson.annotations.SerializedName

data class MovieModel(
    @SerializedName("title") val movieName: String,
    @SerializedName("director") val directorName: String,
    @SerializedName("producer") val producerName: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("episode_id") val episode: Int
)

package com.test.test.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieListingEntity(
    val movieName: String,
    val directorName: String,
    val producerName: String,
    val releaseDate: String,
    val episode: Int,
    val charactersId:List<Int>,
    @PrimaryKey val id:Int? = null
)
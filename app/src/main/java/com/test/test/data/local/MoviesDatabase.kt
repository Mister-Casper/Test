package com.test.test.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MovieListingEntity::class],
    version = 1
)
abstract class MoviesDatabase :RoomDatabase(){
    abstract val moviesDao:MoviesDao
}
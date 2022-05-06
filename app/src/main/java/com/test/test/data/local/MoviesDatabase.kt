package com.test.test.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(
    entities = [MovieListingEntity::class,CharacterListingEntry::class],
    version = 1
)
@TypeConverters(CharactersIdConverter::class)
abstract class MoviesDatabase :RoomDatabase(){
    abstract val moviesDao:MoviesDao
    abstract val charactersDao:CharactersDao
}
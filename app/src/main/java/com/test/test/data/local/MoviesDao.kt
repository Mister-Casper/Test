package com.test.test.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieListings(
        moviesListingEntities: List<MovieListingEntity>
    )

    @Query(
        """
        SELECT *
        FROM MovieListingEntity
        WHERE LOWER(movieName) LIKE '%' || LOWER(:query) || '%' 
        ORDER BY episode
    """
    )
    suspend fun searchMovieListing(query: String): List<MovieListingEntity>

    @Query("SELECT movieName FROM MovieListingEntity WHERE episode =(:episodeId)")
    suspend fun getEpisodeName(episodeId: Int) : String
}
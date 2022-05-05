package com.test.test.data.mapper

import com.test.test.data.local.MovieListingEntity
import com.test.test.data.remote.MovieModel
import com.test.test.data.util.ReleaseDateConverter
import com.test.test.domain.model.MovieListing

fun MovieListingEntity.toMovieListing(releaseDateConverter: ReleaseDateConverter): MovieListing {
    return MovieListing(
        movieName = movieName,
        directorName = directorName,
        producerName = producerName,
        year = releaseDateConverter.convertToYears(releaseDate),
        episode = episode
    )
}

fun MovieModel.toMovieListingEntity(): MovieListingEntity {
    return MovieListingEntity(
        movieName = movieName,
        directorName = directorName,
        producerName = producerName,
        releaseDate = releaseDate,
        episode = episode
    )
}
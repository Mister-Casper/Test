package com.test.test.data.mapper

import androidx.core.text.isDigitsOnly
import com.test.test.data.local.CharacterListingEntry
import com.test.test.data.local.MovieListingEntity
import com.test.test.data.remote.CharacterModel
import com.test.test.data.remote.MovieModel
import com.test.test.data.util.ReleaseDateConverter
import com.test.test.domain.model.CharacterListing
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
        episode = episode,
        charactersId = characters.map {
            it.split("/").last { it.isDigitsOnly() && it.isNotBlank() }.toInt()
        }
    )
}

fun CharacterListingEntry.toCharacterListing(): CharacterListing {
    return CharacterListing(
        characterName = characterName,
        characterGender = characterGender,
        characterBirthday = characterBirthday
    )
}

fun CharacterModel.toCharacterLingEntity(): CharacterListingEntry {
    return CharacterListingEntry(
        characterName = characterName,
        characterGender = characterGender,
        characterBirthday = characterBirthYear,
        characterPlanet = characterPlanet.split("/").last { it.isDigitsOnly() && it.isNotBlank() }
            .toInt()
    )
}
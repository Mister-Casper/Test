package com.test.test.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterListingEntry(

    val characterName: String,
    val characterGender: String,
    val characterBirthday: String,
    val characterPlanet: Int,
    @PrimaryKey val id: Int? = null
)
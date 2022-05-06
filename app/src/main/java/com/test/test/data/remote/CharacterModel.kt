package com.test.test.data.remote

import com.google.gson.annotations.SerializedName

data class CharacterModel(
    @SerializedName("name") val characterName: String,
    @SerializedName("gender") val characterGender: String,
    @SerializedName("birth_year") val characterBirthYear: String,
    @SerializedName("homeworld") val characterPlanet: String
)

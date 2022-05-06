package com.test.test.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class CharactersIdConverter {

    private val gson = Gson()

    @TypeConverter
    fun toCharacters(characters: String?): List<Int> {
        if (characters == null) {
            return Collections.emptyList()
        }
        val type: Type = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(characters, type)
    }

    @TypeConverter
    fun fromCharacters(characters: List<Int>): String {
        val type: Type = object : TypeToken<List<Int>>() {}.type
        return gson.toJson(characters, type)
    }
}
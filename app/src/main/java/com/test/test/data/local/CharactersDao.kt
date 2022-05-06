package com.test.test.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharactersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacterListings(
        characterListingsEntries: List<CharacterListingEntry>
    )

    @Query("SELECT * FROM CharacterListingEntry WHERE id = (:characterId)")
    suspend fun getCharacterById(characterId: Int): CharacterListingEntry?

    @Query("SELECT * FROM CharacterListingEntry")
    suspend fun getLocalCharacters(): List<CharacterListingEntry>
}
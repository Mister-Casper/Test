package com.test.test.presentation.movieCharacters

import com.test.test.domain.model.CharacterListing
import com.test.test.util.StringResource
import java.util.*

data class CharacterListingsState (
    val movieName:String = "",
    val episodeNum:Int = 0,
    val characters:List<CharacterListing> = Collections.emptyList(),
    val isLoading:Boolean = false,
    val errorMassage:StringResource ?= null
        )
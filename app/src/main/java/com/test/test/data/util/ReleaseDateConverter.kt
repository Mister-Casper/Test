package com.test.test.data.util

import javax.inject.Inject

class ReleaseDateConverter @Inject constructor(){

    fun convertToYears(releaseDate:String):String{
        return releaseDate.split("-")[0]
    }

}
package com.github.mohamedwael.marvelcharslibrary.characters.data

import com.github.mohamedwael.marvelcharslibrary.characters.data.model.CharactersResponse
import retrofit2.http.GET

interface MarvelApi {

    @GET("random")
    suspend fun getCharacters(): CharactersResponse

}

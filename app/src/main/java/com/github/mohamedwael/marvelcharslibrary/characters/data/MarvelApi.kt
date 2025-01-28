package com.github.mohamedwael.marvelcharslibrary.characters.data

import com.github.mohamedwael.marvelcharslibrary.characters.data.model.MarvelResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MarvelApi {

    @GET("v1/public/characters")
    suspend fun getCharacters(
        @QueryMap queries: Map<String, String>
    ): MarvelResponse

}

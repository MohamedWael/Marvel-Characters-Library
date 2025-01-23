package com.github.mohamedwael.marvelcharslibrary.characterdetails.data

import com.github.mohamedwael.marvelcharslibrary.characters.data.model.MarvelResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface ResourceUriEndPoint {

    @GET
    suspend fun getResourceDetails(@Url resourceUri: String, @QueryMap queryMap: Map<String, String>): MarvelResponse
}
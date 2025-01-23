package com.github.mohamedwael.marvelcharslibrary.characterdetails.domain

import com.github.mohamedwael.marvelcharslibrary.characters.data.model.MarvelResponse

interface ResourceUriRepo {

    suspend fun getResourceDetails(
        resourceUri: String,
        queryMap: Map<String, String>
    ): MarvelResponse
}
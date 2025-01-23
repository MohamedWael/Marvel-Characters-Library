package com.github.mohamedwael.marvelcharslibrary.characterdetails.data

import com.github.mohamedwael.marvelcharslibrary.characterdetails.domain.ResourceUriRepo
import com.github.mohamedwael.marvelcharslibrary.characters.data.model.MarvelResponse
import javax.inject.Inject

class ResourceUriRepoImpl @Inject constructor(private val endPoint: ResourceUriEndPoint) : ResourceUriRepo {

    override suspend fun getResourceDetails(
        resourceUri: String,
        queryMap: Map<String, String>
    ): MarvelResponse = endPoint.getResourceDetails(resourceUri, queryMap)
}

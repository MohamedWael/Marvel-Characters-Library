package com.github.mohamedwael.marvelcharslibrary.characterdetails.domain

import com.github.mohamedwael.marvelcharslibrary.characters.data.MarvelApiException
import com.github.mohamedwael.marvelcharslibrary.characters.data.model.MarvelResponse
import com.github.mohamedwael.marvelcharslibrary.util.ResponseState

interface ResourceUriUseCase {
    suspend fun getResourceDetails(resourceUri: String): ResponseState
}

class ResourceUriUseCaseImpl(private val repo: ResourceUriRepo) : ResourceUriUseCase {


    override suspend fun getResourceDetails(resourceUri: String): ResponseState {
        return try {
            val response = repo.getResourceDetails(resourceUri, mapOf())
            if (response.data == null) {
                return ResponseState.Error(MarvelApiException.EmptyResponseException)
            }
            ResponseState.Success(response.data)
        } catch (e: Exception) {
            ResponseState.Error(e)
        }
    }
}
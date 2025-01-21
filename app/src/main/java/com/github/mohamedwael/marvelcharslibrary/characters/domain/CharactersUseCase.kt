package com.github.mohamedwael.marvelcharslibrary.characters.domain

import com.github.mohamedwael.marvelcharslibrary.characters.data.MarvelApiException
import com.github.mohamedwael.marvelcharslibrary.characters.data.model.MarvelResponse
import com.github.mohamedwael.marvelcharslibrary.util.ResponseState
import javax.inject.Inject

interface CharactersUseCase {
    suspend fun getCharacters(
        name: String?,
        limit: Int,
        offset: Int
    ): ResponseState
}

class CharactersUseCaseImpl @Inject constructor(private val repo: CharactersRepo) :
    CharactersUseCase {

    override suspend fun getCharacters(
        name: String?,
        limit: Int,
        offset: Int,
    ): ResponseState {
        val queries = mutableMapOf(
            "limit" to limit.toString(),
            "offset" to offset.toString(),
        )
        if (name != null) {
            queries["name"] = name
        }

        return try {
            val response = repo.getCharacters(queries)
            if (response.data == null) {
                return ResponseState.Error(MarvelApiException.EmptyResponseException)
            }
            ResponseState.Success(response.data)
        } catch (e: Exception) {
            ResponseState.Error(e)
        }
    }
}

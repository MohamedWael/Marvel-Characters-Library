package com.github.mohamedwael.marvelcharslibrary.characters.data

import com.github.mohamedwael.marvelcharslibrary.characters.data.model.MarvelResponse
import com.github.mohamedwael.marvelcharslibrary.characters.domain.CharactersRepo
import javax.inject.Inject

class CharactersRepoImpl @Inject constructor(private val api: MarvelApi) : CharactersRepo {

    override suspend fun getCharacters(
        queries: Map<String, String>
    ): MarvelResponse = run {
        try {

            api.getCharacters(queries)
        } catch (e: Exception) {
            throw handleMarvelApiExceptions(e)
        }
    }
}

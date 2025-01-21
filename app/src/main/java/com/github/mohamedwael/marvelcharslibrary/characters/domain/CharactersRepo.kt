package com.github.mohamedwael.marvelcharslibrary.characters.domain

import com.github.mohamedwael.marvelcharslibrary.characters.data.model.MarvelResponse

interface CharactersRepo {
    suspend fun getCharacters(queries: Map<String, String>): MarvelResponse
}
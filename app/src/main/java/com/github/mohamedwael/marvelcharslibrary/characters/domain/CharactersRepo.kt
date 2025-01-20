package com.github.mohamedwael.marvelcharslibrary.characters.domain

import com.github.mohamedwael.marvelcharslibrary.characters.data.model.CharactersResponse

interface CharactersRepo {
    suspend fun getCharacters(): CharactersResponse
}
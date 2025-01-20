package com.github.mohamedwael.marvelcharslibrary.characters.domain

import com.github.mohamedwael.marvelcharslibrary.characters.data.model.CharactersResponse
import javax.inject.Inject

interface CharactersUseCase {
    suspend fun getCharacters(): CharactersResponse
}

class CharactersUseCaseImpl @Inject constructor(private val repo: CharactersRepo) : CharactersUseCase {

    override suspend fun getCharacters(): CharactersResponse {

        return repo.getCharacters()
    }
}

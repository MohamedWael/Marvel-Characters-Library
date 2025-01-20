package com.github.mohamedwael.marvelcharslibrary.characters.data

import com.github.mohamedwael.marvelcharslibrary.characters.data.model.CharactersResponse
import com.github.mohamedwael.marvelcharslibrary.characters.domain.CharactersRepo
import javax.inject.Inject

class CharactersRepoImpl @Inject constructor(private val api: MarvelApi) : CharactersRepo {

    override suspend fun getCharacters(): CharactersResponse = api.getCharacters()
}

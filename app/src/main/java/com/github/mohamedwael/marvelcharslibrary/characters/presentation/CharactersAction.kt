package com.github.mohamedwael.marvelcharslibrary.characters.presentation

sealed class CharactersAction {
    data class LoadCharacters(
        val limit: Int = 20,
        val offset: Int = 0,
        val total: Int,
        val count: Int
    ) : CharactersAction()
    data class SearchCharacters(val query: String) : CharactersAction()
}
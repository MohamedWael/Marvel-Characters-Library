package com.github.mohamedwael.marvelcharslibrary.searchcharacter.presentation

sealed class SearchCharacterAction {
    data class SearchCharacters(val query: String) : SearchCharacterAction()
}
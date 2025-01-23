package com.github.mohamedwael.marvelcharslibrary.characterdetails.presentation

sealed class CharacterDetailsAction {
    data class LoadCharacterDetails(val characterJson: String) : CharacterDetailsAction()
}
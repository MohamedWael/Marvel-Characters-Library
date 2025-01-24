package com.github.mohamedwael.marvelcharslibrary.searchcharacter.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mohamedwael.marvelcharslibrary.characters.domain.CharactersUseCase
import com.github.mohamedwael.marvelcharslibrary.characters.presentation.DEFAULT_LIMIT
import com.github.mohamedwael.marvelcharslibrary.util.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchCharacterViewModel @Inject constructor(
    private val charactersRepo: CharactersUseCase
) : ViewModel() {

    private val _characters = MutableStateFlow<ResponseState>(ResponseState.Idle)
    val characters: StateFlow<ResponseState> = _characters

    fun dispatch(action: SearchCharacterAction) {
        when (action) {
            is SearchCharacterAction.SearchCharacters -> {
                searchCharacters(action.query)
            }
        }
    }

    private fun searchCharacters(query: String) {
        viewModelScope.launch {
            if (query.isNotEmpty()){
                _characters.value = ResponseState.Loading
                val response = charactersRepo.getCharacters(
                    name = query,
                    limit = DEFAULT_LIMIT,
                    offset = 0
                )
                _characters.value = response
            }

        }
    }
}
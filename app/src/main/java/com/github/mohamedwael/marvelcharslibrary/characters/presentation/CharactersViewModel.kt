package com.github.mohamedwael.marvelcharslibrary.characters.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mohamedwael.marvelcharslibrary.characters.data.model.MarvelCharacter
import com.github.mohamedwael.marvelcharslibrary.characters.data.model.MarvelData
import com.github.mohamedwael.marvelcharslibrary.characters.domain.CharactersUseCase
import com.github.mohamedwael.marvelcharslibrary.util.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val DEFAULT_LIMIT = 20

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val charactersRepo: CharactersUseCase
) : ViewModel() {

    private val TAG = "CharactersViewModel"
    private val characterList = mutableSetOf<MarvelCharacter>()
    private val _characters = MutableStateFlow<ResponseState>(ResponseState.Loading)
    val characters: StateFlow<ResponseState> = _characters

    fun dispatch(action: CharactersAction) {
        when (action) {
            is CharactersAction.LoadCharacters -> {
                if (action.total >= action.count)
                    getCharacters(
                        limit = action.limit,
                        offset = action.offset
                    )
            }

            is CharactersAction.SearchCharacters -> getCharacters(action.query)
        }
    }

    private fun getCharacters(query: String? = null, limit: Int = DEFAULT_LIMIT, offset: Int = 0) {
        _characters.value = ResponseState.Loading
        viewModelScope.launch {
            val response = charactersRepo.getCharacters(
                name = query,
                limit = limit,
                offset = offset
            )

            if (response is ResponseState.Success<*>) {
                val marvelData = response.data as MarvelData?
                characterList.addAll(marvelData?.results ?: emptyList())
                _characters.value = ResponseState.Success(marvelData?.copy(results = characterList.toList()))
            } else {
                _characters.value = response
            }
        }
    }
}
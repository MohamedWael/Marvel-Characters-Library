package com.github.mohamedwael.marvelcharslibrary.characters.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mohamedwael.marvelcharslibrary.characters.domain.CharactersRepo
import com.github.mohamedwael.marvelcharslibrary.util.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val charactersRepo: CharactersRepo
) : ViewModel() {

    private val _characters = MutableLiveData<ResponseState>()
    val characters: LiveData<ResponseState> = _characters

    fun getCharacters() {
        _characters.value = ResponseState.Loading
        viewModelScope.launch {
            try {
                val response = charactersRepo.getCharacters()
                _characters.value = ResponseState.Success(response)
            } catch (e: Exception) {
                _characters.value = ResponseState.Error(e)
            }
        }
    }
}
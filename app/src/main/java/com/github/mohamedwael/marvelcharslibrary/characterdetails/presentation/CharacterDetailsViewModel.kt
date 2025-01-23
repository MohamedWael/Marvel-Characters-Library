package com.github.mohamedwael.marvelcharslibrary.characterdetails.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mohamedwael.marvelcharslibrary.characterdetails.domain.ResourceUriUseCase
import com.github.mohamedwael.marvelcharslibrary.characters.data.model.MarvelCharacter
import com.github.mohamedwael.marvelcharslibrary.characters.data.model.MarvelData
import com.github.mohamedwael.marvelcharslibrary.util.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val useCase: ResourceUriUseCase
) : ViewModel() {
    private val resourceCache = mutableMapOf<String, String>()

    private val TAG = "CharacterDetailsViewModel"
    private val _character = MutableStateFlow<ResponseState>(ResponseState.Loading)
    val character: StateFlow<ResponseState> = _character

    fun dispatch(action: CharacterDetailsAction) {
        when (action) {
            is CharacterDetailsAction.LoadCharacterDetails -> loadCharacterDetails(action)
        }
    }

    private fun loadCharacterDetails(characterDetails: CharacterDetailsAction.LoadCharacterDetails) {
        try {
            val marvelCharacter =
                Json.decodeFromString<MarvelCharacter>(characterDetails.characterJson)
            _character.value = ResponseState.Success(marvelCharacter)

        } catch (e: Exception) {
            _character.value = ResponseState.Error(e)
        }

    }


    fun loadCharacterImage(resourceUri: String?): MutableState<String> {
        Log.d(TAG, "loadCharacterImage: $resourceUri")
        val image = mutableStateOf("")
        if (resourceCache.containsKey(resourceUri)) {
            image.value = resourceCache[resourceUri] ?: ""
            return image
        } else {
            viewModelScope.launch {
                resourceUri?.also {
                    when (val response = useCase.getResourceDetails(resourceUri)) {
                        is ResponseState.Success<*> -> {
                            val marvelData = (response.data as MarvelData)
                            val thumbnails =
                                marvelData.results?.map { it.thumbnail }?.filterNotNull()
                            resourceCache[resourceUri] = thumbnails?.firstOrNull()?.getFullPath() ?: ""
                            Log.d(TAG, "loadCharacterImage: loaded ${thumbnails.toString()}")
                            image.value = thumbnails?.firstOrNull()?.getFullPath() ?: ""
                        }

                        else -> {
                            // no op
                        }
                    }
                }
            }
            return image
        }
    }
}

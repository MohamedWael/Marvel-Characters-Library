package com.github.mohamedwael.marvelcharslibrary.util

sealed class ResponseState {
    data class Success<T>(val data: T) : ResponseState()
    data class Error(val exception: Exception) : ResponseState()
    data object Loading : ResponseState()
    data object Idle : ResponseState()
}

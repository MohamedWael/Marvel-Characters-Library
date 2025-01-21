package com.github.mohamedwael.marvelcharslibrary.characters.data

import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

sealed class MarvelApiException(message: String) : Exception(message) {
    data object EmptyResponseException : MarvelApiException("")
    data object LimitExceededException : MarvelApiException("Limit greater than 100.")
    data object InvalidLimitException : MarvelApiException("Limit invalid or below 1.")
    data object InvalidParameterException : MarvelApiException("Invalid or unrecognized parameter.")
    data object EmptyParameterException : MarvelApiException("Empty parameter.")
    data object InvalidOrderingException : MarvelApiException("Invalid or unrecognized ordering parameter.")
    data object TooManyValuesException : MarvelApiException("Too many values sent to a multi-value list filter.")
    data object InvalidFilterValueException : MarvelApiException("Invalid value passed to filter.")
    data object UnknownErrorException : MarvelApiException("An unknown error occurred.")
    data object NoInternetException: MarvelApiException("No internet Connection")
    data object TimeoutException: MarvelApiException("Time Out Error")
    data object ServerErrorException: MarvelApiException("Server error")
}

internal fun handleMarvelApiExceptions(throwable: Throwable): MarvelApiException {
    return when (throwable) {
        is HttpException -> {
            when (throwable.code()) {
                409 -> {
                    val errorMessage =
                        throwable.response()?.errorBody()?.string() ?: "Unknown error"
                    when {
                        errorMessage.contains("Limit greater than 100") -> MarvelApiException.LimitExceededException
                        errorMessage.contains("Limit invalid or below 1") -> MarvelApiException.InvalidLimitException
                        errorMessage.contains("Invalid or unrecognized parameter") -> MarvelApiException.InvalidParameterException
                        errorMessage.contains("Empty parameter") -> MarvelApiException.EmptyParameterException
                        errorMessage.contains("Invalid or unrecognized ordering parameter") -> MarvelApiException.InvalidOrderingException
                        errorMessage.contains("Too many values sent to a multi-value list filter") -> MarvelApiException.TooManyValuesException
                        errorMessage.contains("Invalid value passed to filter") -> MarvelApiException.InvalidFilterValueException
                        else -> MarvelApiException.UnknownErrorException
                    }
                }
                // handle server errors
                in 500..599 -> MarvelApiException.ServerErrorException
                else -> MarvelApiException.UnknownErrorException
            }
        }

        // Handle timeout exceptions
        is SocketTimeoutException -> MarvelApiException.TimeoutException

        // Handle no internet connection exceptions
        is IOException -> MarvelApiException.NoInternetException

        else -> MarvelApiException.UnknownErrorException
    }
}

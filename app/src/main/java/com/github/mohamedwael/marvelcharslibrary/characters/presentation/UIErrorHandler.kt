package com.github.mohamedwael.marvelcharslibrary.characters.presentation

import com.github.mohamedwael.marvelcharslibrary.R
import com.github.mohamedwael.marvelcharslibrary.characters.data.MarvelApiException
import com.github.mohamedwael.marvelcharslibrary.util.ResponseState

fun handleUIErrorHandler(error: Throwable): Int = when (error) {
    is MarvelApiException -> {
        when (error) {
            MarvelApiException.EmptyResponseException -> R.string.error_empty_response
            MarvelApiException.NoInternetException, MarvelApiException.TimeoutException -> R.string.error_no_internet
            MarvelApiException.ServerErrorException -> R.string.error_server_unreachable
            MarvelApiException.UnknownErrorException -> R.string.error_generic
            MarvelApiException.LimitExceededException -> R.string.error_limit_greater_than_100
            MarvelApiException.InvalidLimitException -> R.string.error_limit_invalid_or_below_1
            MarvelApiException.InvalidParameterException -> R.string.error_invalid_or_unrecognized_parameter
            MarvelApiException.EmptyParameterException -> R.string.error_empty_parameter
            MarvelApiException.InvalidOrderingException -> R.string.error_invalid_or_unrecognized_ordering_parameter
            MarvelApiException.TooManyValuesException -> R.string.error_too_many_values
            MarvelApiException.InvalidFilterValueException -> R.string.error_invalid_filter_value
        }
    }

    else -> R.string.error_generic
}

fun ResponseState.Error.getErrorMessageResource() = handleUIErrorHandler(this.exception)

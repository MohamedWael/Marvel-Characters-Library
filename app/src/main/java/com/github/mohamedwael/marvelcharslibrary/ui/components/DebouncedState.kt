package com.github.mohamedwael.marvelcharslibrary.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun <T> rememberDebouncedState(
    initialValue: T,
    delayMillis: Long
): State<T> {
    val debouncedState = remember { mutableStateOf(initialValue) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(initialValue) {
        scope.launch {
            delay(delayMillis)
            debouncedState.value = initialValue
        }
    }

    return debouncedState
}
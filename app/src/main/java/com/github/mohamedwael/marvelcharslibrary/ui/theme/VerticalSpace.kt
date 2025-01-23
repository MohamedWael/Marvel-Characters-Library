package com.github.mohamedwael.marvelcharslibrary.ui.theme

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun VerticalSpace(height: Dp = 8.dp) {
    Spacer(modifier = Modifier.height(height))
}
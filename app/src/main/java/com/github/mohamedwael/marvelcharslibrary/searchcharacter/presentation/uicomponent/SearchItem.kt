package com.github.mohamedwael.marvelcharslibrary.searchcharacter.presentation.uicomponent

import android.widget.SearchView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.github.mohamedwael.marvelcharslibrary.R
import com.github.mohamedwael.marvelcharslibrary.characters.data.model.MarvelCharacter
import com.github.mohamedwael.marvelcharslibrary.characters.presentation.uicomponents.chars

@Composable
fun SearchView(modifier: Modifier = Modifier, marvelCharacter: MarvelCharacter) {

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(marvelCharacter.thumbnail?.getFullPath())
                .crossfade(true)
                .build(),
            contentDescription = marvelCharacter.name ?: "",
            error = painterResource(id = R.drawable.marvel_fallback),
            contentScale = ContentScale.Crop,
            fallback = painterResource(id = R.drawable.marvel_fallback),
            modifier = Modifier.size(70.dp)
        )

        Text(
            text = marvelCharacter.name ?: "",
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
    }
}

@Preview
@Composable
private fun SearchViewPreview() {
    SearchView(marvelCharacter = chars.first())
}
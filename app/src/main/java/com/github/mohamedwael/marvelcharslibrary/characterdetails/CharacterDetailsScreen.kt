package com.github.mohamedwael.marvelcharslibrary.characterdetails

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.github.mohamedwael.marvelcharslibrary.R
import com.github.mohamedwael.marvelcharslibrary.characters.data.model.EventItem
import com.github.mohamedwael.marvelcharslibrary.characters.data.model.MarvelCharacter
import com.github.mohamedwael.marvelcharslibrary.characters.presentation.uicomponents.chars
import com.github.mohamedwael.marvelcharslibrary.ui.components.GifImage
import com.github.mohamedwael.marvelcharslibrary.ui.components.MarvelDialogWithImage
import com.github.mohamedwael.marvelcharslibrary.ui.theme.VerticalSpace
import com.github.mohamedwael.marvelcharslibrary.ui.theme.backgroundGradient


@Composable
fun CharacterDetailsScreen(
    character: MarvelCharacter, modifier: Modifier = Modifier,
    imageUrl : (String?)-> MutableState<String>?,
    onBackClick: () -> Unit = {}

) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundGradient)
            .verticalScroll(state = scrollState)
    ) {
        Box(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(character.thumbnail?.getFullPath())
                    .crossfade(true)
                    .build(),
                contentDescription = character.name,
                contentScale = ContentScale.Crop,
                fallback = painterResource(id = R.drawable.marvel_fallback),
                modifier = Modifier.height(300.dp)
            )

            IconButton(
                modifier = Modifier.align(Alignment.TopStart),
                onClick = onBackClick
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            if (!character.name.isNullOrEmpty()){
                Text(
                    text = stringResource(R.string.name),
                    color = Color.Red,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.wrapContentWidth()
                )
                VerticalSpace(4.dp)
                Text(
                    text = character.name ?: "",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.wrapContentWidth()
                )
            }

            if (!character.description.isNullOrEmpty()){
                VerticalSpace(8.dp)
                Text(
                    text = stringResource(R.string.description),
                    color = Color.Red,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.wrapContentWidth()
                )
                VerticalSpace(4.dp)
                Text(
                    text = character.description ?: "",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.wrapContentWidth()
                )
            }


            VerticalSpace(16.dp)

            character.comics?.items?.let { EventView(title = stringResource(R.string.comics), it, imageUrl) }
            character.series?.items?.let { EventView(title = stringResource(R.string.series), it, imageUrl) }
            character.stories?.items?.let { EventView(title = stringResource(R.string.stories), it, imageUrl) }
            character.events?.items?.let { EventView(title = stringResource(R.string.events), it, imageUrl) }
        }
    }
}

@Composable
fun EventView(
    title: String,
    events: List<EventItem>,
    imageUrl: (String?) -> MutableState<String>?,
    modifier: Modifier = Modifier
) {
    if (events.isNotEmpty()){

        Column(modifier = modifier) {
            Text(
                text = title,
                color = Color.Red,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.wrapContentWidth()
            )
            LazyRow(
                contentPadding = PaddingValues(
                    start = 0.dp,
                    end = 12.dp,
                    top = 4.dp,
                    bottom = 4.dp
                )
            ) {
                items(events) { event ->
                    EventItemView(event, imageUrl)
                }
            }
        }
    }
}

@Composable
fun EventItemView(event: EventItem, imageUrl: (String?) -> MutableState<String>?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(end = 8.dp)
            .animateContentSize()
    ) {

        val showDialog = remember { mutableStateOf(false) }
        val loading = remember { mutableStateOf(true) }
        Box(modifier = Modifier
            .height(130.dp)
            .width(100.dp)) {

            AsyncImage(
                model = imageUrl(event.resourceURI)?.value ?: "",
                contentDescription = event.name ?: "",
                contentScale = ContentScale.Crop,
                fallback = painterResource(id = R.drawable.marvel_fallback),
                error = painterResource(id = R.drawable.marvel_fallback),
                onLoading = {
                    loading.value = true
                },
                onSuccess = {
                    loading.value = false
                },
                onError = {
                    loading.value = false
                },
                modifier = Modifier
                    .clickable {
                        showDialog.value = true
                    }
                    .height(130.dp)
                    .width(100.dp)
            )

            if (loading.value){
                GifImage(
                    modifier = Modifier.fillMaxSize(),
                    gifResourceDrawable = R.drawable.marvel_loading
                )
            }
        }

        Text(
            text = event.name ?: "",
            color = Color.White,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .wrapContentWidth()
                .width(100.dp)
        )

        if (showDialog.value) {
            MarvelDialogWithImage(
                imageUrl = imageUrl(event.resourceURI)?.value ?: "",
                title = event.name ?: "",
                onDismissRequest = {
                    showDialog.value = false
                },
            )
        }

    }
}

@Preview
@Composable
private fun CharacterDetailsScreenPreview() {
    CharacterDetailsScreen(
        character = chars.first(),
        imageUrl = { mutableStateOf(comicMockImagePath) }
    )
}

private val comicMockImagePath = "http://i.annihil.us/u/prod/marvel/i/mg/6/b0/5d4306b39f6b8.jpg"

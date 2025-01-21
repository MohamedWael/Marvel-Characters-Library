package com.github.mohamedwael.marvelcharslibrary.characters.presentation.uicomponents

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.mohamedwael.marvelcharslibrary.R
import com.github.mohamedwael.marvelcharslibrary.ui.theme.MarvelCharactersLibraryTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.github.mohamedwael.marvelcharslibrary.characters.data.model.MarvelCharacter
import com.github.mohamedwael.marvelcharslibrary.characters.data.model.MarvelData
import com.github.mohamedwael.marvelcharslibrary.characters.data.model.Thumbnail
import com.github.mohamedwael.marvelcharslibrary.characters.presentation.DEFAULT_LIMIT

@Composable
fun MarvelCharacterList(
    modifier: Modifier = Modifier,
    marvelData: MarvelData?,
    isLoading: Boolean,
    loadMoreItems: (limit: Int, offset: Int, total: Int, count: Int) -> Unit
) {
    val marvelCharacters = remember { mutableStateOf<List<MarvelCharacter>>(emptyList()) }

    LaunchedEffect(marvelData) {
        marvelData?.results?.let { newResults ->
            marvelCharacters.value = marvelCharacters.value + newResults
        }
    }

    // Update loading whenever isLoading changes
    val loading = remember(isLoading) { isLoading }

    val lazyListState = rememberLazyListState()


    LaunchedEffect(lazyListState) {

        snapshotFlow { lazyListState.layoutInfo }
            .collect { layoutInfo ->

                val totalItems = layoutInfo.totalItemsCount
                val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

                // Load more items if the user has scrolled to the end
                if (lastVisibleItem >= totalItems - 1) {
                    loadMoreItems(
                        marvelData?.limit ?: DEFAULT_LIMIT,
                        marvelData?.offset ?: marvelCharacters.value.size,
                        marvelData?.total ?: 0,
                        marvelData?.count ?: 0
                    )
                }
            }
    }

    Column(modifier = modifier) {
        // Header with Marvel logo and search icon
        Box(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxWidth(),
        ) {
            Image(
                painter = painterResource(id = R.drawable.marvel_logo),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .width(100.dp)
                    .padding(vertical = 8.dp)
            )
            IconButton(
                modifier = Modifier.align(Alignment.CenterEnd),
                onClick = { /* Handle search */ }
            ) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(45.dp)
                )
            }
        }


        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = lazyListState
        ) {
            // Display items
            items(marvelCharacters.value) { item ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    CharacterCard(
                        characterName = item.name,
                        imagePath = item.thumbnail?.getFullPath(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = {
                                // Handle character click
                                item.series
                            }),
                    )
                }
            }

            // Show a progress indicator at the end if loading
            if (loading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCharacterListScreen() {

    MarvelCharactersLibraryTheme {
        MarvelCharacterList(
            marvelData = fakeMarvelData,
            isLoading = false,
            loadMoreItems = { limit, offset, total, count ->

            }
        )
    }
}

private val imagePath =
    "https://cdn.marvel.com/u/prod/marvel/i/mg/3/e0/661e9b6428e34/standard_incredible"
private val charNames = listOf(
    "Spiderman",
    "Ironman",
    "Hulk",
    "Thor",
    "Captain America",
    "Black Widow",
)
private val chars = (1..30).toList().map {
    MarvelCharacter(
        name = charNames.random(),
        thumbnail = Thumbnail(path = imagePath, extension = "jpg")
    )
}

private val fakeMarvelData = MarvelData(
    total = 100,
    offset = 0,
    limit = 20,
    count = 20,
    results = chars
)

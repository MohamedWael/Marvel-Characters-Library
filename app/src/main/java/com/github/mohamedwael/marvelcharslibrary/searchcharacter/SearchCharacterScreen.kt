@file:OptIn(ExperimentalMaterial3Api::class)

package com.github.mohamedwael.marvelcharslibrary.searchcharacter

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.mohamedwael.marvelcharslibrary.R
import com.github.mohamedwael.marvelcharslibrary.characters.data.model.MarvelCharacter
import com.github.mohamedwael.marvelcharslibrary.characters.data.model.MarvelData
import com.github.mohamedwael.marvelcharslibrary.characters.data.model.Thumbnail
import com.github.mohamedwael.marvelcharslibrary.characters.presentation.getErrorMessageResource
import com.github.mohamedwael.marvelcharslibrary.characters.presentation.uicomponents.CharacterCard
import com.github.mohamedwael.marvelcharslibrary.characters.presentation.uicomponents.chars
import com.github.mohamedwael.marvelcharslibrary.characters.presentation.uicomponents.fakeMarvelData
import com.github.mohamedwael.marvelcharslibrary.searchcharacter.presentation.uicomponent.SearchView
import com.github.mohamedwael.marvelcharslibrary.ui.components.rememberDebouncedState
import com.github.mohamedwael.marvelcharslibrary.ui.theme.searchScreenBackgroundColor
import com.github.mohamedwael.marvelcharslibrary.util.ResponseState

@Composable
fun SearchCharacterScreen(
    onBackClick: () -> Unit,
    searchCharacter: (String) -> Unit,
    onCharacterClick: (MarvelCharacter) -> Unit,
    charactersState: State<ResponseState>
) {
    var searchQuery by remember { mutableStateOf("") }
    val debouncedSearchQuery = rememberDebouncedState(searchQuery, 300)

    LaunchedEffect(debouncedSearchQuery.value) {
        searchCharacter(debouncedSearchQuery.value)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(searchScreenBackgroundColor)) {
        // Search bar with back button
        TopAppBar(
            title = {
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text(stringResource(R.string.search), color = Color.Gray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back),
                        tint = Color.White
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Black
            )
        )

        // Display search results
        when (val response = charactersState.value) {
            is ResponseState.Success<*> -> {
                val marvelData = response.data as MarvelData
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(marvelData?.results ?: emptyList()) { character ->
                        SearchView(modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onCharacterClick(character) }, marvelCharacter = character)
                    }

                    item {
                        if (marvelData?.results?.isEmpty() == true) {
                            Text(
                                "No results found",
                                modifier = Modifier.padding(16.dp),
                                color = Color.White,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }

            is ResponseState.Error -> {
                // Show error message
                Text(
                    text = stringResource(R.string.error, response.getErrorMessageResource()),
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }

            ResponseState.Loading -> {
                CircularProgressIndicator(modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp))
            }
            else -> {}
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchCharacterScreen() {

    val mockResponseState = remember { mutableStateOf<ResponseState>(ResponseState.Success(fakeMarvelData)) }

    SearchCharacterScreen(
        onBackClick = {  },
        searchCharacter = {  },
        onCharacterClick = {  },
        charactersState = mockResponseState
    )
}

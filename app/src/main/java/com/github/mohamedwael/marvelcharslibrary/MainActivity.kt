package com.github.mohamedwael.marvelcharslibrary

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.github.mohamedwael.marvelcharslibrary.characters.data.model.MarvelData
import com.github.mohamedwael.marvelcharslibrary.characters.presentation.CharactersAction
import com.github.mohamedwael.marvelcharslibrary.characters.presentation.CharactersViewModel
import com.github.mohamedwael.marvelcharslibrary.characters.presentation.getErrorMessageResource
import com.github.mohamedwael.marvelcharslibrary.characters.presentation.uicomponents.MarvelCharacterList
import com.github.mohamedwael.marvelcharslibrary.ui.theme.MarvelCharactersLibraryTheme
import com.github.mohamedwael.marvelcharslibrary.util.ResponseState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel: CharactersViewModel by viewModels()
        viewModel.dispatch(CharactersAction.LoadCharacters(total = 0, count = 0))
        setContent {
            val charactersState = viewModel.characters.collectAsState()

            MarvelCharactersLibraryTheme {
                Scaffold {

                    when (val response = charactersState.value) {
                        is ResponseState.Error -> Toast.makeText(
                            this,
                            response.getErrorMessageResource(),
                            Toast.LENGTH_LONG
                        ).show()

                        else -> MarvelCharacterList(
                            modifier = Modifier.padding(top = it.calculateTopPadding()),
                            if (response is ResponseState.Success<*>) response.data as MarvelData else null,
                            isLoading = response is ResponseState.Loading
                        ) { limit, offset, total, count ->
                            Log.d("MainActivity", "onCreate: Load more limit: $limit, offset: $offset, total: $total, count: $count")
                            viewModel.dispatch(CharactersAction.LoadCharacters(
                                limit = limit,
                                offset = offset + 1,
                                total = total,
                                count = count
                            ))
                        }
                    }
                }

            }
        }
    }
}

package com.github.mohamedwael.marvelcharslibrary

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.github.mohamedwael.marvelcharslibrary.characterdetails.CharacterDetailsScreen
import com.github.mohamedwael.marvelcharslibrary.characterdetails.presentation.CharacterDetailsAction
import com.github.mohamedwael.marvelcharslibrary.characterdetails.presentation.CharacterDetailsViewModel
import com.github.mohamedwael.marvelcharslibrary.characters.data.model.MarvelCharacter
import com.github.mohamedwael.marvelcharslibrary.characters.data.model.MarvelData
import com.github.mohamedwael.marvelcharslibrary.characters.presentation.CharactersAction
import com.github.mohamedwael.marvelcharslibrary.characters.presentation.CharactersViewModel
import com.github.mohamedwael.marvelcharslibrary.characters.presentation.getErrorMessageResource
import com.github.mohamedwael.marvelcharslibrary.characters.presentation.uicomponents.MarvelCharacterList
import com.github.mohamedwael.marvelcharslibrary.ui.theme.MarvelCharactersLibraryTheme
import com.github.mohamedwael.marvelcharslibrary.util.ResponseState
import com.github.mohamedwael.marvelcharslibrary.util.navTypeOf
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf

@Serializable
object CharacterListScreen

@Serializable
data class CharacterDetails(val marvelCharacter: String)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val charactersViewModel: CharactersViewModel by viewModels()
        charactersViewModel.dispatch(CharactersAction.LoadCharacters(total = 0, count = 0))

        val characterDetailsViewModel: CharacterDetailsViewModel by viewModels()
        setContent {
            MarvelCharactersLibraryTheme {
                Scaffold {
                    val navController = rememberNavController()
                    val lazyListState = rememberLazyListState()
                    NavHost(
                        navController = navController,
                        startDestination = CharacterListScreen,
                        modifier = Modifier.padding(top = it.calculateTopPadding())
                    ) {
                        composable<CharacterListScreen>() {
                            val charactersState = charactersViewModel.characters.collectAsState()
                            when (val response = charactersState.value) {
                                is ResponseState.Error -> showErrorMessage(response.getErrorMessageResource())

                                else -> MarvelCharacterList(
                                    modifier = Modifier,
                                    if (response is ResponseState.Success<*>) response.data as MarvelData else null,
                                    isLoading = response is ResponseState.Loading,
                                    lazyListState = lazyListState,
                                    onCharacterClick = { character ->
                                        navController.navigate(
                                            CharacterDetails(
                                                Json.encodeToString(
                                                    character
                                                )
                                            )
                                        )
                                    },
                                ) { limit, offset, total, count ->
                                    charactersViewModel.dispatch(
                                        CharactersAction.LoadCharacters(
                                            limit = limit,
                                            offset = offset,
                                            total = total,
                                            count = count
                                        )
                                    )
                                }
                            }
                        }

                        composable<CharacterDetails>(
                            typeMap = mapOf(typeOf<CharacterDetails>() to navTypeOf<CharacterDetails>())
                        ) {
                            val characterDetails = it.toRoute<CharacterDetails>()
                            characterDetailsViewModel.dispatch(
                                CharacterDetailsAction.LoadCharacterDetails(
                                    characterDetails.marvelCharacter
                                )
                            )
                            val state = characterDetailsViewModel.character.collectAsState().value

                            when (state) {
                                is ResponseState.Error -> showErrorMessage(state.getErrorMessageResource())
                                else -> {
                                    val marvelCharacter =
                                        (state as? ResponseState.Success<*>)?.data as? MarvelCharacter
                                    marvelCharacter?.let {
                                        CharacterDetailsScreen(
                                            marvelCharacter,
                                            imageUrl = characterDetailsViewModel::loadCharacterImage,
                                            onBackClick = { navController.popBackStack() })
                                    }

                                }
                            }
                        }
                    }
                }

            }
        }
    }

    private fun showErrorMessage(errorMessageResource: Int) {
        Toast.makeText(
            this,
            errorMessageResource,
            Toast.LENGTH_LONG
        ).show()
    }
}

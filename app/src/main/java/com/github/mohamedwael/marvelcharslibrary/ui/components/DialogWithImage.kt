package com.github.mohamedwael.marvelcharslibrary.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import com.github.mohamedwael.marvelcharslibrary.R

@Composable
fun MarvelDialogWithImage(
    onDismissRequest: () -> Unit,
    imageUrl:String,
    title: String,
) {
    val loading = remember { mutableStateOf(true) }
    Dialog(onDismissRequest = { onDismissRequest() }) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    IconButton(onClick = { onDismissRequest() }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                        )
                    }
                }

                Box {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = title,
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
                            .padding(horizontal = 16.dp)
                            .height(150.dp)
                            .fillMaxWidth()
                    )
                    if (loading.value){
                        GifImage(
                            modifier = Modifier.fillMaxSize(),
                            gifResourceDrawable = R.drawable.marvel_loading
                        )
                    }
                }


                Text(
                    text = title,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview
@Composable
private fun DialogWithImagePreview() {
    MarvelDialogWithImage(
        onDismissRequest = {},
        imageUrl = "https://www.example.com/image.jpg",
        title = "Dialog Title"
    )
}
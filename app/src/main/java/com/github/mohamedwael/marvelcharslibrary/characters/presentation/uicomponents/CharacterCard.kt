package com.github.mohamedwael.marvelcharslibrary.characters.presentation.uicomponents

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.github.mohamedwael.marvelcharslibrary.R
import com.github.mohamedwael.marvelcharslibrary.ui.theme.MarvelCharactersLibraryTheme

@Composable
fun CharacterCard(
    characterName: String?,
    imagePath: String?,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(190.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black)
    ) {
        Box {
            // Background image
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imagePath)
                    .crossfade(true)
                    .build(),
                contentDescription = characterName,
                contentScale = ContentScale.Crop,
                fallback = painterResource(id = R.drawable.marvel_fallback),
                modifier = Modifier.fillMaxSize()
            )

            characterName?.let {
                ParallelogramText(
                    text = characterName,
                    modifier = Modifier

                        .align(Alignment.BottomStart)
                        .padding(start = 24.dp, bottom = 16.dp)
                )

            }

        }
    }
}

@Composable
fun ParallelogramText(text:String , modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.wrapContentWidth()
    ) {
        Parallelogram(
            color = Color.White,
            modifier = Modifier
                .height(50.dp)
                .width(250.dp)
        )
        Text(
            text = text,
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.wrapContentWidth()
                .align(Alignment.Center)

        )
    }
}

@Composable
fun Parallelogram(
    color: Color,
    modifier: Modifier = Modifier
) {

//    // TextMeasurer to measure and draw text
//    val textMeasurer = rememberTextMeasurer()
//
//    // Define the text to be drawn
//    val text = "Hello, Compose Canvas! Canvas Canvas"
//
//    // Measure the text
//    val textLayoutResult = textMeasurer.measure(
//        text = text,
//        style = TextStyle(
//            color = Color.Black,
//            fontSize = 18.sp,
//            fontWeight = FontWeight.Bold,
//        )
//    )

    Canvas(modifier = modifier) {
        val width = size.width //textLayoutResult.size.width + 60.dp.toPx()
        val height = size.height
        val offset = width * 0.08f


        drawPath(
            path = androidx.compose.ui.graphics.Path().apply {
                moveTo(offset, 0f) // Top-left corner
                lineTo(width, 0f) // Top-right corner
                lineTo(width - offset, height) // Bottom-right corner
                lineTo(0f, height) // Bottom-left corner
                close() // Connect back to the top-left corner
            },
            color = color
        )

//        drawText(
//            textLayoutResult = textLayoutResult,
//            topLeft = Offset(
//                x = width / 2 - textLayoutResult.size.width / 2,
//                y = height / 2 - textLayoutResult.size.height / 2
//            )
//        )
    }
}

@Preview
@Composable
private fun CharacterCardPreview() {
    MarvelCharactersLibraryTheme {
        Column {
            CharacterCard(
                characterName = "3-D Man",
                imagePath = "https://cdn.marvel.com/u/prod/marvel/i/mg/3/e0/661e9b6428e34/standard_incredible.jpg",
                modifier = Modifier.fillMaxWidth()
            )
        }

    }
}



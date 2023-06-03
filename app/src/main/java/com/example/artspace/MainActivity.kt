package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Art()
                }
            }
        }
    }

    @Composable
    fun Art() {
        var index by remember {
            mutableStateOf(0)
        }
        val image = when (index) {
            0 -> R.drawable.image1
            1 -> R.drawable.image2
            2 -> R.drawable.image3
            3 -> R.drawable.image4
            4 -> R.drawable.image5
            5 -> R.drawable.image6
            6 -> R.drawable.image7
            else -> R.drawable.image8
        }
        val data = Util.getData(LocalContext.current.assets.open("imagecap.csv"))
        Column(modifier = Modifier.fillMaxSize()) {
            ArtBoard(
                image = painterResource(id = image),
                content = data[index].title,
                modifier = Modifier.weight(1f)
            )
            ArtDetail(
                title = data[index].title,
                artist = data[index].maker,
                year = data[index].year
            )
            NavigationButton(
                showPrev = index != 0,
                onPrev = { index-- },
                showNext = index != data.size - 1,
                onNext = { index++ })
        }
    }

    @Composable
    fun ArtBoard(image: Painter, content: String, modifier: Modifier) {

        Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(
                modifier = Modifier
                    .padding(40.dp)
                    .fillMaxHeight(),
                shadowElevation = 8.dp
            ) {
                Image(
                    painter = image,
                    contentDescription = content,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(40.dp)
                        .fillMaxHeight()
                )
            }
        }
    }

    @Composable
    fun ArtDetail(title: String, artist: String, year: Int) {

        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Column(
                    modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally).padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Medium,
                        fontSize = 24.sp,
                        lineHeight = 24.sp,
                        textAlign = TextAlign.Center
                    )
                    Text(text = String.format("%s (%d)", artist, year), fontSize = 24.sp)
                }
            }
        }
    }

    @Composable
    fun NavigationButton(
        showPrev: Boolean,
        onPrev: () -> Unit,
        showNext: Boolean,
        onNext: () -> Unit
    ) {
        Row(horizontalArrangement = if(showPrev && showNext) Arrangement.SpaceBetween else Arrangement.Center, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp, vertical = 20.dp)) {
            if (showPrev) {
                Button(onClick = { onPrev() }) {
                    Text(text = stringResource(id = R.string.previous), fontSize = 20.sp)
                }
            }
            if (showNext) {
                Button(onClick = { onNext() }) {
                    Text(text = stringResource(id = R.string.next), fontSize = 20.sp)
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        ArtSpaceTheme {
            Art()
        }
    }
}


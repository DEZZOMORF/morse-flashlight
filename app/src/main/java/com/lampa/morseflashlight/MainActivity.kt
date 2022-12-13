package com.lampa.morseflashlight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lampa.morseflashlight.ui.FlashlightButton
import com.lampa.morseflashlight.ui.theme.MorseFlashlightTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mainViewModel: MainViewModel = hiltViewModel()
            val flashlightState by mainViewModel.flashlightState.collectAsState()
            MainScreen(
                flashlightState = flashlightState,
                onAction = mainViewModel::onAction
            )
        }
    }
}

@Composable
fun MainScreen(
    flashlightState: Boolean,
    onAction: (FlashlightAction) -> Unit
) {
    MorseFlashlightTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row {
                    OffButton { onAction(FlashlightAction.Off) }
                    Spacer(modifier = Modifier.width(12.dp))
                    SimpleButton { onAction(FlashlightAction.Torch) }
                }
                Box(
                    modifier = Modifier
                        .height(12.dp)
                        .width(12.dp)
                        .clip(shape = CircleShape)
                        .background(if (flashlightState) Color.Yellow else Color.Gray)
                )
                Row {
                    SosButton { onAction(FlashlightAction.Morse("SOS")) }
                    Spacer(modifier = Modifier.width(12.dp))
                    StroboscopeButton { onAction(FlashlightAction.Stroboscope) }
                }
            }
        }
    }
}

@Composable
fun OffButton(onClick: () -> Unit) {
    FlashlightButton(
        onClick = onClick,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_flashlight_off_24),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .padding(8.dp)
        )
    }
}

@Composable
fun SimpleButton(onClick: () -> Unit) {
    FlashlightButton(
        onClick = onClick,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_flashlight_on_24),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .padding(8.dp)
        )
    }
}

@Composable
fun SosButton(onClick: () -> Unit) {
    FlashlightButton(
        onClick = onClick,
    ) {
        Text(
            text = "SOS",
            style = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            ),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(8.dp)
        )
    }
}

@Composable
fun StroboscopeButton(onClick: () -> Unit) {
    FlashlightButton(
        onClick = onClick,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_visibility_off_24),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .padding(8.dp)
        )
    }
}
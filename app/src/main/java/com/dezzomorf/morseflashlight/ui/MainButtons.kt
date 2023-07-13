package com.dezzomorf.morseflashlight.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dezzomorf.morseflashlight.R
import com.dezzomorf.morseflashlight.`object`.FlashlightAction
import com.dezzomorf.morseflashlight.ui.theme.defaultArrangementSpace
import com.dezzomorf.morseflashlight.viewmodel.MainViewModel

@Composable
fun MainButtons(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {

        val context = LocalContext.current
        val mainViewModel: MainViewModel = hiltViewModel()

        Row(
            horizontalArrangement = Arrangement.spacedBy(defaultArrangementSpace),
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
        ) {

            OffButton(
                Modifier.weight(1f)
            ) {
                mainViewModel.onAction(FlashlightAction.Off)
            }

            SimpleButton(
                Modifier.weight(1f)
            ) {
                mainViewModel.onAction(FlashlightAction.Torch)
            }
        }

        val flashlightState by mainViewModel.flashlightState.collectAsState()
        Box(
            modifier = Modifier
                .height(12.dp)
                .width(12.dp)
                .clip(shape = CircleShape)
                .background(if (flashlightState) Color.Yellow else Color.Gray)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(defaultArrangementSpace),
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {

            SosButton(
                Modifier.weight(1f)
            ) {
                mainViewModel.onAction(
                    FlashlightAction.Morse(
                        context.getString(R.string.sos),
                        true
                    )
                )
            }

            StroboscopeButton(
                Modifier.weight(1f)
            ) {
                mainViewModel.onAction(FlashlightAction.Stroboscope)
            }
        }
    }
}

@Composable
fun OffButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    RoundCornerSquareButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxSize()
    ) {
        ImageButtonContent(
            image = R.drawable.ic_baseline_flashlight_off_24
        )
    }
}

@Composable
fun SimpleButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    RoundCornerSquareButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxSize()
    ) {
        ImageButtonContent(
            image = R.drawable.ic_baseline_flashlight_on_24
        )
    }
}

@Composable
fun SosButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    RoundCornerSquareButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxSize()
    ) {
        TextButtonContent(
            text = LocalContext.current.getString(R.string.sos)
        )
    }
}

@Composable
fun StroboscopeButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    RoundCornerSquareButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxSize()
    ) {
        ImageButtonContent(
            image = R.drawable.ic_baseline_visibility_off_24
        )
    }
}
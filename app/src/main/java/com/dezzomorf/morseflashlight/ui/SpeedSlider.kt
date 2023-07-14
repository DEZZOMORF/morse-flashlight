package com.dezzomorf.morseflashlight.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.dezzomorf.morseflashlight.R
import com.dezzomorf.morseflashlight.ui.theme.ViewBackground
import com.dezzomorf.morseflashlight.ui.theme.defaultContentPadding
import com.dezzomorf.morseflashlight.viewmodel.MainViewModel

@Composable
fun MorseSpeedSlider(modifier: Modifier = Modifier) {
    RoundCornerBox(modifier = modifier) {
        val mainViewModel: MainViewModel = hiltViewModel()
        SpeedSlider(
            modifier = Modifier
                .background(ViewBackground)
                .padding(horizontal = defaultContentPadding),
            morseSpeed = { mainViewModel.setSpeed(it) }
        )
    }
}

@Composable
fun SpeedSlider(modifier: Modifier = Modifier, morseSpeed: (Float) -> Unit) {
    var sliderPosition by remember { mutableStateOf(MainViewModel.SPEED) }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(defaultContentPadding)
    ) {
        Text(
            text = stringResource(R.string.speed),
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )

        Slider(
            value = sliderPosition,
            valueRange = 0.2f..1f,
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Color.White
            ),
            onValueChange = {
                sliderPosition = it
                morseSpeed(1.2f - it)
            }
        )
    }
}
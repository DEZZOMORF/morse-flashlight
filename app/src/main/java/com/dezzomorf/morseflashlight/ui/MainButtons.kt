package com.dezzomorf.morseflashlight.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dezzomorf.morseflashlight.R
import com.dezzomorf.morseflashlight.`object`.FlashlightAction
import com.dezzomorf.morseflashlight.ui.theme.defaultArrangementSpace
import com.dezzomorf.morseflashlight.ui.theme.defaultContentPadding

@Composable
fun MainButtons(
    flashlightState: Boolean,
    onAction: (FlashlightAction) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(defaultArrangementSpace),
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
        ) {
            OffButton(
                Modifier
                    .weight(1f)
            ) {
                onAction(FlashlightAction.Off)
            }
            SimpleButton(
                Modifier
                    .weight(1f)
            ) {
                onAction(FlashlightAction.Torch)
            }
        }
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
                Modifier
                    .weight(1f)
            ) {
                onAction(FlashlightAction.Morse("SOS", true))
            }
            StroboscopeButton(
                Modifier
                    .weight(1f)
            ) {
                onAction(FlashlightAction.Stroboscope)
            }
        }
    }
}

@Composable
fun OffButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    RoundCornerButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_flashlight_off_24),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .padding(defaultContentPadding)
        )
    }
}

@Composable
fun SimpleButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    RoundCornerButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_flashlight_on_24),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .padding(defaultContentPadding)
        )
    }
}

@Composable
fun SosButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    val defaultTextStyle = TextStyle(
        textAlign = TextAlign.Center,
        fontSize = 60.sp,
        fontWeight = FontWeight.Bold
    )
    var textStyle by remember { mutableStateOf(defaultTextStyle) }
    var readyToDraw by remember { mutableStateOf(false) }

    RoundCornerButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            text = "SOS",
            style = textStyle,
            maxLines = 1,
            softWrap = false,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(defaultContentPadding)
                .drawWithContent {
                    if (readyToDraw) drawContent()
                },
            onTextLayout = { textLayoutResult ->
                if (textLayoutResult.didOverflowWidth) {
                    textStyle = textStyle.copy(fontSize = textStyle.fontSize * 0.9)
                } else {
                    readyToDraw = true
                }
            },
        )
    }
}

@Composable
fun StroboscopeButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    RoundCornerButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_visibility_off_24),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .padding(defaultContentPadding)
        )
    }
}
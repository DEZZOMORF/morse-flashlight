package com.lampa.morseflashlight.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lampa.morseflashlight.`object`.FlashlightAction
import com.lampa.morseflashlight.ui.theme.defaultContentPadding

@Composable
fun MorseUi(
    onAction: (FlashlightAction) -> Unit
) {
    var text by rememberSaveable { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(modifier = Modifier
            .fillMaxSize()
            .weight(3f)) {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Set your text") },
                shape = RoundedCornerShape(24.dp, 24.dp, 0.dp, 0.dp),
                modifier = Modifier.fillMaxSize()
            )
        }
        Row(modifier = Modifier
            .fillMaxSize()
            .weight(1f)) {
            MorseButton(Modifier.fillMaxSize()) {
                onAction(FlashlightAction.Morse(text))
            }
        }
    }
}

@Composable
fun MorseButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    val defaultTextStyle = TextStyle(
        textAlign = TextAlign.Center,
        fontSize = 60.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White
    )
    var textStyle by remember { mutableStateOf(defaultTextStyle) }
    var readyToDraw by remember { mutableStateOf(false) }

    BottomRoundCornerButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = "TEXT TO MORSE",
            style = textStyle,
            maxLines = 1,
            softWrap = false,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = defaultContentPadding, vertical = 4.dp)
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
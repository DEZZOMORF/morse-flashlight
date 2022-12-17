package com.dezzomorf.morseflashlight.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dezzomorf.morseflashlight.R
import com.dezzomorf.morseflashlight.`object`.FlashlightAction
import com.dezzomorf.morseflashlight.ui.theme.defaultContentPadding

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MorseUi(
    morseText: String,
    onAction: (FlashlightAction) -> Unit
) {
    var text by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .align(Alignment.Start)
        ) {
            var lastVisibleTextIndex by rememberSaveable { mutableStateOf(0) }
            if (morseText.length < lastVisibleTextIndex) lastVisibleTextIndex = 0
            val trimmedText = morseText.substring(lastVisibleTextIndex)
            Text(
                text = trimmedText,
                maxLines = 1,
                softWrap = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Bottom)
                    .clip(shape = RoundedCornerShape(24.dp, 24.dp, 0.dp, 0.dp))
                    .background(Color.Gray)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                onTextLayout = { textLayoutResult ->
                    if (textLayoutResult.hasVisualOverflow) {
                        lastVisibleTextIndex += textLayoutResult.getLineEnd(
                            lineIndex = 0,
                            visibleEnd = true
                        ) - 1
                    }
                }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(5f)
        ) {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text(stringResource(R.string.set_your_text)) },
                shape = RoundedCornerShape(0.dp, 0.dp, 0.dp, 0.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                colors = TextFieldDefaults.textFieldColors(
                    focusedLabelColor = Color.White,
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.White
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        onAction(FlashlightAction.Morse(text, false))
                    }
                ),
                modifier = Modifier.fillMaxSize()
            )
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(2f)
        ) {
            MorseButton(Modifier.fillMaxSize()) {
                onAction(FlashlightAction.Morse(text, false))
            }
        }
    }
}

@Composable
fun MorseButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    val defaultTextStyle = TextStyle(
        textAlign = TextAlign.Center,
        fontSize = 60.sp,
        fontWeight = FontWeight.Bold
    )
    var textStyle by remember { mutableStateOf(defaultTextStyle) }
    var readyToDraw by remember { mutableStateOf(false) }

    BottomRoundCornerButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.text_to_morse),
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
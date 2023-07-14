package com.dezzomorf.morseflashlight.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.dezzomorf.morseflashlight.R
import com.dezzomorf.morseflashlight.`object`.FlashlightAction
import com.dezzomorf.morseflashlight.`object`.MorseCode
import com.dezzomorf.morseflashlight.`object`.MorseSymbol
import com.dezzomorf.morseflashlight.ui.theme.AppBackground
import com.dezzomorf.morseflashlight.ui.theme.ViewBackground
import com.dezzomorf.morseflashlight.ui.theme.defaultContentPadding
import com.dezzomorf.morseflashlight.viewmodel.MainViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MorseUi(modifier: Modifier = Modifier) {
    RoundCornerBox(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            var text by rememberSaveable { mutableStateOf("") }
            val mainViewModel: MainViewModel = hiltViewModel()
            val textProgressState by mainViewModel.textProgressState.collectAsState()
            val textOnMorseState by mainViewModel.textOnMorseState.collectAsState()

            MorseTextProgress(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(horizontal = defaultContentPadding, vertical = 4.dp),
                morseText = textProgressState,
                textOnMorse = textOnMorseState
            )

            val color = AppBackground.copy(alpha = 0.5f)
            val keyboardController = LocalSoftwareKeyboardController.current
            TextField(
                value = text,
                onValueChange = { text = it },
                placeholder = { Text(stringResource(R.string.set_your_text)) },
                shape = RoundedCornerShape(0.dp, 0.dp, 0.dp, 0.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                colors = TextFieldDefaults.textFieldColors(
                    focusedLabelColor = Color.White,
                    cursorColor = Color.White,
                    focusedIndicatorColor = color,
                    unfocusedIndicatorColor = Color.Transparent,
                    backgroundColor = color
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        mainViewModel.onAction(FlashlightAction.Morse(text, false))
                    }
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .weight(4f)
                    .clickable { keyboardController?.show() }
            )

            TextToMorseButton(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxSize()
                    .weight(1f),
                onClick = {
                    mainViewModel.onAction(FlashlightAction.Morse(text, false))
                }
            )
        }
    }
}

@Composable
fun TextToMorseButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clickable { onClick() }
    ) {
        TextButtonContent(
            text = stringResource(R.string.text_to_morse),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = defaultContentPadding)
        )
    }
}

@Composable
fun MorseTextProgress(
    modifier: Modifier = Modifier,
    morseText: String,
    textOnMorse: List<MorseCode>
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        var lastVisibleTextIndex by rememberSaveable { mutableStateOf(0) }
        if (morseText.length < lastVisibleTextIndex) lastVisibleTextIndex = 0
        val trimmedText = morseText.substring(lastVisibleTextIndex)

        Text(
            text = trimmedText,
            maxLines = 1,
            softWrap = false,
            modifier = Modifier
                .wrapContentSize(Alignment.CenterStart)
                .weight(6f)
                .align(Alignment.CenterVertically),
            onTextLayout = { textLayoutResult ->
                if (textLayoutResult.hasVisualOverflow) {
                    lastVisibleTextIndex += textLayoutResult.getLineEnd(
                        lineIndex = 0,
                        visibleEnd = true
                    ) - 1
                }
            }
        )

        val openDialog = remember { mutableStateOf(false) }
        MorseInfoDialog(
            dialogState = openDialog.value,
            onDialogStateChange = { openDialog.value = it },
            textOnMorse = textOnMorse
        )

        if (textOnMorse.isNotEmpty()) {
            IconButton(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                onClick = {
                    openDialog.value = true
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_info_24),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun MorseInfoDialog(
    modifier: Modifier = Modifier,
    dialogState: Boolean = false,
    onDialogPositiveButtonClicked: (() -> Unit)? = null,
    onDialogStateChange: ((Boolean) -> Unit)? = null,
    onDismissRequest: (() -> Unit)? = null,
    textOnMorse: List<MorseCode>
) {

    if (dialogState) {
        AlertDialog(
            backgroundColor = ViewBackground,
            title = null,
            text = null,
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
            modifier = modifier,
            shape = RoundedCornerShape(24.dp),onDismissRequest = {
                onDialogStateChange?.invoke(false)
                onDismissRequest?.invoke()
            },
            buttons = {
                Column {
                    LazyColumn(
                        modifier = Modifier
                            .height(400.dp)
                            .fillMaxWidth(),
                        contentPadding = PaddingValues(
                            top = defaultContentPadding
                        )
                    ) {
                        items(textOnMorse) { item: MorseCode ->
                            MorseInfoItem(
                                modifier = Modifier.padding(horizontal = defaultContentPadding),
                                item = item
                            )
                        }
                    }

                    Divider(color = AppBackground, thickness = 1.dp)

                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .clickable {
                                onDialogStateChange?.invoke(false)
                                onDialogPositiveButtonClicked?.invoke()
                            }
                    ) {
                        Text(
                            text = stringResource(android.R.string.ok),
                            color = MaterialTheme.colors.onSurface,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentSize(Alignment.Center)
                                .align(Alignment.Center)
                                .padding(12.dp)
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun MorseInfoItem(modifier: Modifier = Modifier, item: MorseCode) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        if (item == MorseCode.SPACE) {
            Divider(color = AppBackground, thickness = 1.dp)
        } else {
            Text(
                modifier = modifier
                    .width(16.dp)
                    .background(AppBackground),
                text = item.name,
                textAlign = TextAlign.Center
            )
        }

        MorseCode.getMorseSymbols(item).forEach { morseSymbol ->
            when (morseSymbol) {
                MorseSymbol.DOT -> Dot(modifier = Modifier.align(Alignment.CenterVertically))
                MorseSymbol.DASH -> Dash(modifier = Modifier.align(Alignment.CenterVertically))
                else -> {}
            }
        }
    }
}

@Composable
fun Dot(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .width(8.dp)
            .height(8.dp)
            .clip(shape = CircleShape)
            .background(Color.White)
    )
}

@Composable
fun Dash(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .width(16.dp)
            .height(8.dp)
            .clip(shape = RoundedCornerShape(4.dp, 4.dp, 4.dp, 4.dp))
            .background(Color.White)
    )
}
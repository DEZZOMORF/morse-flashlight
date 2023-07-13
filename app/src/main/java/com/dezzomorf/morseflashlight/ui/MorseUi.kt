package com.dezzomorf.morseflashlight.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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

    var text by rememberSaveable { mutableStateOf("") }
    val mainViewModel: MainViewModel = hiltViewModel()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1.1f)
                .align(Alignment.Start)
        ) {
            val textProgressState by mainViewModel.textProgressState.collectAsState()
            val textOnMorseState by mainViewModel.textOnMorseState.collectAsState()
            MorseTextProgress(textProgressState, textOnMorseState)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(4f)
        ) {
            val color = ViewBackground.copy(alpha = 0.5f)
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
                modifier = Modifier.fillMaxSize()
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            MorseSpeedSlider(mainViewModel::setSpeed)
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(ViewBackground.copy(alpha = 0.5f))
        )

        Box(
            contentAlignment =  Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .weight(1.0f)
        ) {
            MorseButton(
                Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            ) {
                mainViewModel.onAction(FlashlightAction.Morse(text, false))
            }
        }
    }
}

@Composable
fun MorseButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    BottomRoundCornerButton(
        onClick = onClick,
        modifier = modifier
    ) {
        TextButtonContent(
            text = stringResource(R.string.text_to_morse),
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .padding(horizontal = defaultContentPadding)
        )
    }
}

@Composable
fun MorseTextProgress(morseText: String, textOnMorse: List<MorseCode>) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(24.dp, 24.dp, 0.dp, 0.dp))
            .background(ViewBackground)
            .padding(horizontal = 16.dp, vertical = 4.dp)
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
    val textPaddingAll = 24.dp
    val dialogShape = RoundedCornerShape(24.dp)

    if (dialogState) {
        AlertDialog(
            onDismissRequest = {
                onDialogStateChange?.invoke(false)
                onDismissRequest?.invoke()
            },
            backgroundColor = ViewBackground,
            title = null,
            text = null,
            buttons = {

                Column() {
                    Column(
                        Modifier
                            .height(400.dp)
                            .padding(all = textPaddingAll)
                            .verticalScroll(rememberScrollState())
                            .fillMaxWidth()
                    ) {
                        textOnMorse.forEach {
                            val morseSymbols = MorseCode.getMorseSymbols(it)
                            Row {
                                if (it == MorseCode.SPACE) {
                                    Spacer(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(1.dp)
                                            .background(AppBackground)
                                    )
                                } else {
                                    Text(text = "${it.name} -> ")
                                }

                                morseSymbols.forEach { morseSymbol ->
                                    Spacer(modifier = Modifier.width(2.dp))
                                    when (morseSymbol) {
                                        MorseSymbol.DOT -> Dot(modifier = Modifier.align(Alignment.CenterVertically))
                                        MorseSymbol.DASH -> Dash(modifier = Modifier.align(Alignment.CenterVertically))
                                        else -> {}
                                    }
                                }
                            }
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
            },
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
            modifier = modifier,
            shape = dialogShape
        )
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

@Composable
fun MorseSpeedSlider(morseSpeed: (Float) -> Unit) {
    var sliderPosition by remember { mutableStateOf(MainViewModel.SPEED) }
    Row(
        modifier = Modifier
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(0.dp, 0.dp, 0.dp, 0.dp))
            .background(ViewBackground)
            .padding(horizontal = defaultContentPadding)
    ) {
        Text(
            text = stringResource(R.string.speed),
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
        Spacer(
            modifier = Modifier
                .width(4.dp)
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
            },
            modifier = Modifier
                .fillMaxHeight()
        )
    }
}
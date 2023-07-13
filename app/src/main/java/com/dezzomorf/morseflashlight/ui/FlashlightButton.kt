package com.dezzomorf.morseflashlight.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dezzomorf.morseflashlight.ui.theme.ViewBackground
import com.dezzomorf.morseflashlight.ui.theme.defaultContentPadding

@Composable
fun RoundCornerSquareButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable() (BoxScope.() -> Unit)
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(24.dp))
            .background(ViewBackground)
            .clickable { onClick() }
            .then(modifier)) {
        content.invoke(this)
    }
}

@Composable
fun ImageButtonContent(
    modifier: Modifier = Modifier,
    @DrawableRes image: Int
) {
    Image(
        painter = painterResource(id = image),
        contentDescription = null,
        modifier = modifier
            .fillMaxSize()
            .padding(defaultContentPadding)
    )
}

@Composable
fun TextButtonContent(
    modifier: Modifier = Modifier,
    text: String
) {
    val defaultTextStyle = TextStyle(
        textAlign = TextAlign.Center,
        fontSize = 60.sp,
        fontWeight = FontWeight.Bold
    )
    var textStyle by remember { mutableStateOf(defaultTextStyle) }
    var readyToDraw by remember { mutableStateOf(false) }

    Text(
        text = text,
        style = textStyle,
        maxLines = 1,
        softWrap = false,
        modifier = modifier
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

@Composable
fun BottomRoundCornerButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable() (BoxScope.() -> Unit)
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(0.dp, 0.dp, 24.dp, 24.dp))
            .background(ViewBackground)
            .clickable { onClick() }
            .then(modifier)) {
        content.invoke(this)
    }
}
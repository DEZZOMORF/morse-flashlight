package com.dezzomorf.morseflashlight.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.dezzomorf.morseflashlight.ui.theme.ViewBackground

@Composable
fun RoundCornerButton(
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
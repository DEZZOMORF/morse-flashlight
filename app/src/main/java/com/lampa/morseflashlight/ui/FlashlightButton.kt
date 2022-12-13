package com.lampa.morseflashlight.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FlashlightButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable() (BoxScope.() -> Unit)
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(120.dp)
            .width(120.dp)
            .padding(8.dp)
            .clip(shape = RoundedCornerShape(24.dp))
            .background(Color.Gray)
            .clickable { onClick() }
            .then(modifier)) {
        content.invoke(this)
    }
}
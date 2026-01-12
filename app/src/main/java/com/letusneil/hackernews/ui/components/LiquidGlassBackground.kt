package com.letusneil.hackernews.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.letusneil.hackernews.ui.theme.LiquidGlassColors

@Composable
fun LiquidGlassBackground(
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean = true,
    content: @Composable BoxScope.() -> Unit
) {
    val gradient = if (isDarkTheme) {
        LiquidGlassColors.backgroundGradientDark
    } else {
        LiquidGlassColors.backgroundGradientLight
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        content()
    }
}

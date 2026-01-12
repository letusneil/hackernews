package com.letusneil.hackernews.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Immutable
object LiquidGlassColors {
    // Background gradient colors
    val backgroundDark1 = Color(0xFF1a1a2e)
    val backgroundDark2 = Color(0xFF16213e)
    val backgroundDark3 = Color(0xFF0f3460)

    val backgroundLight1 = Color(0xFFe8f4f8)
    val backgroundLight2 = Color(0xFFd4e5ed)
    val backgroundLight3 = Color(0xFFb8d4e3)

    // Glass card colors
    val glassWhiteHigh = Color.White.copy(alpha = 0.25f)
    val glassWhiteMedium = Color.White.copy(alpha = 0.15f)
    val glassWhiteLow = Color.White.copy(alpha = 0.10f)
    val glassWhiteSubtle = Color.White.copy(alpha = 0.05f)

    // Border colors
    val borderHighlight = Color.White.copy(alpha = 0.5f)
    val borderSubtle = Color.White.copy(alpha = 0.1f)

    // Text colors
    val textPrimary = Color.White
    val textSecondary = Color.White.copy(alpha = 0.7f)
    val textTertiary = Color.White.copy(alpha = 0.5f)

    // Accent colors
    val accentBlue = Color(0xFF64B5F6)
    val accentCyan = Color(0xFF81D4FA)
    val accentOrange = Color(0xFFFFB74D)
    val accentGreen = Color(0xFF81C784)
    val accentPink = Color(0xFFF48FB1)

    // Snackbar
    val snackbarBackground = Color.White.copy(alpha = 0.9f)
    val snackbarContent = Color.Black

    // Gradients
    val backgroundGradientDark: Brush
        get() = Brush.linearGradient(
            colors = listOf(backgroundDark1, backgroundDark2, backgroundDark3, backgroundDark1),
            start = Offset(0f, 0f),
            end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
        )

    val backgroundGradientLight: Brush
        get() = Brush.linearGradient(
            colors = listOf(backgroundLight1, backgroundLight2, backgroundLight3, backgroundLight1),
            start = Offset(0f, 0f),
            end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
        )

    val glassBackgroundGradient: Brush
        get() = Brush.linearGradient(
            colors = listOf(glassWhiteHigh, glassWhiteLow)
        )

    val glassBorderGradient: Brush
        get() = Brush.linearGradient(
            colors = listOf(borderHighlight, borderSubtle)
        )

    val appBarGradient: Brush
        get() = Brush.verticalGradient(
            colors = listOf(glassWhiteMedium, glassWhiteSubtle)
        )
}

val LocalLiquidGlassColors = staticCompositionLocalOf { LiquidGlassColors }

@Composable
fun liquidGlassColors(): LiquidGlassColors = LocalLiquidGlassColors.current

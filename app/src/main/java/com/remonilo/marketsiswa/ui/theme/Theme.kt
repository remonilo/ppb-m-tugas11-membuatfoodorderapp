package com.remonilo.marketsiswa.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme =
    lightColorScheme(
        primary = Indigo,
        onPrimary = Color.White,
        primaryContainer = Indigo,
        onPrimaryContainer = Color.White,
        secondaryContainer = IndigoContainer,
        onSecondaryContainer = Indigo,
        background = Slate50,
        onBackground = Slate900,
        surface = Color.White,
        onSurface = Slate900,
        onSurfaceVariant = Slate500,
        outlineVariant = Slate200,
    )

@Composable
fun MarketSiswaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content,
    )
}

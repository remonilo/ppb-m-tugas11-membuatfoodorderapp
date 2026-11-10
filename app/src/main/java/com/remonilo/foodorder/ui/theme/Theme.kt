package com.remonilo.foodorder.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme =
    lightColorScheme(
        primary = Purple,
        onPrimary = Color.White,
        primaryContainer = PurpleContainer,
        onPrimaryContainer = OnPurpleContainer,
    )

private val DarkColorScheme =
    darkColorScheme(
        primary = PurpleLight,
        primaryContainer = PurpleContainerDark,
        onPrimaryContainer = PurpleContainer,
    )

@Composable
fun FoodOrderTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit,
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            // ikon status bar mengikuti tema aplikasi, bukan tema sistem
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content,
    )
}

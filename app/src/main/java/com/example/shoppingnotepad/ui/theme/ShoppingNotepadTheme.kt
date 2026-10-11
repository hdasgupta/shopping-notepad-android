package com.example.shoppingnotepad.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF2F6B46),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD7EED9),
    onPrimaryContainer = Color(0xFF153B24),
    secondary = Color(0xFF52634F),
    onSecondary = Color.White,
    tertiary = Color(0xFF7A5A31),
    background = Color(0xFFF5F7F1),
    surface = Color(0xFFFFFFFF),
    surfaceVariant = Color(0xFFE8EDE3),
    onSurface = Color(0xFF1A211A),
    onSurfaceVariant = Color(0xFF434B41),
    outline = Color(0xFF7B8578)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFA6D5AE),
    onPrimary = Color(0xFF12391F),
    primaryContainer = Color(0xFF285236),
    onPrimaryContainer = Color(0xFFD7EED9),
    secondary = Color(0xFFBBCDB5),
    tertiary = Color(0xFFE7C18D),
    background = Color(0xFF111711),
    surface = Color(0xFF191F19),
    surfaceVariant = Color(0xFF343C32),
    onSurface = Color(0xFFE2E8DE),
    onSurfaceVariant = Color(0xFFC1CABD)
)

@Composable
fun ShoppingNotepadTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) DarkColors else LightColors,
        content = content
    )
}

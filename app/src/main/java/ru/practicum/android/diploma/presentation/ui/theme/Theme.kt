package ru.practicum.android.diploma.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Blue,
    onPrimary = WhiteUniversal,
    background = WhiteDay,
    onBackground = BlackDay,
    surfaceVariant = Gray,
    onSurfaceVariant = Gray,
    onSurface = BlackUniversal,
    secondaryContainer = LightGray,
    error = Red,
    scrim = Background,
)

private val DarkColorScheme = darkColorScheme(
    primary = Blue,
    onPrimary = WhiteUniversal,
    background = WhiteNight,
    onBackground = BlackNight,
    surfaceVariant = LightGray,
    onSurfaceVariant = WhiteUniversal,
    onSurface = BlackUniversal,
    secondaryContainer = Gray,
    error = Red,
    scrim = Background,
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        content = content,
    )
}

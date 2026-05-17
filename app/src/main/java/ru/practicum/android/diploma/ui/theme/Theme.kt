package ru.practicum.android.diploma.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = Blue,
    onPrimary = WhiteUniversal,
    background = WhiteDay,
    onBackground = BlackDay,
    surface = WhiteDay,
    onSurface = BlackDay,
    surfaceVariant = LightGray,
    onSurfaceVariant = Gray,
    outline = LightGray,
    error = Red,
    onError = WhiteUniversal,
    scrim = BackgroundOverlay,
)

private val DarkColors = darkColorScheme(
    primary = Blue,
    onPrimary = WhiteUniversal,
    background = WhiteNight,
    onBackground = BlackNight,
    surface = WhiteNight,
    onSurface = BlackNight,
    surfaceVariant = WhiteNight,
    onSurfaceVariant = Gray,
    outline = Gray,
    error = Red,
    onError = WhiteUniversal,
    scrim = BackgroundOverlay,
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = AppTypography,
        content = content,
    )
}

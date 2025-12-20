package ru.practicum.android.diploma.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = blue,
    onPrimary = white,
    secondary = red,
    onSecondary = white,
    background = white,
    onBackground = black,
    surface = white,
    onSurface = black,
    surfaceVariant = lightGrey,
    onSurfaceVariant = grey,
    error = red,
    onError = white,
    outline = grey,
)

@Composable
fun VacancySearchAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = CustomTypography,
        content = content
    )
}

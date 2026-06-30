package ru.practicum.android.diploma.ui.theme

import androidx.compose.ui.graphics.Color

private const val HEX_BLACK = 0xFF1A1B22
private const val HEX_WHITE = 0xFFFDFDFD
private const val HEX_BLUE = 0xFF3772E7
private const val HEX_RED = 0xFFF56B6C
private const val HEX_GREY = 0xFFAEAFB4
private const val HEX_LIGHT_GREY = 0xFFE6E8EB
private const val BACKGROUND_OVERLAY_ALPHA = 0.5f

val Black = Color(HEX_BLACK)
val White = Color(HEX_WHITE)
val Blue = Color(HEX_BLUE)
val Red = Color(HEX_RED)
val Grey = Color(HEX_GREY)
val LightGrey = Color(HEX_LIGHT_GREY)
val Background = Color(HEX_BLACK).copy(alpha = BACKGROUND_OVERLAY_ALPHA)

val LightPrimary = Blue
val LightOnPrimary = White
val LightSecondary = Grey
val LightOnSecondary = Black
val LightBackground = White
val LightOnBackground = Black
val LightSurface = White
val LightOnSurface = Black
val LightSurfaceVariant = LightGrey
val LightOnSurfaceVariant = Grey
val LightError = Red
val LightSurfaceContainer = LightGrey
val LightInversionOnSurface = Grey

val DarkPrimary = Blue
val DarkOnPrimary = White
val DarkSecondary = Grey
val DarkOnSecondary = Black
val DarkBackground = Black
val DarkOnBackground = White
val DarkSurface = Black
val DarkOnSurface = White
val DarkSurfaceVariant = LightGrey
val DarkOnSurfaceVariant = Grey
val DarkError = Red
val DarkSurfaceContainer = Grey
val DarkInversionOnSurface = White

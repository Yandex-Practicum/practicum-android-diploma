package ru.practicum.android.diploma.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import ru.practicum.android.diploma.R

private val YSDisplay = FontFamily(
    Font(R.font.ys_display_thin, FontWeight.Thin),
    Font(R.font.ys_display_light, FontWeight.Light),
    Font(R.font.ys_display_regular, FontWeight.Normal),
    Font(R.font.ys_display_medium, FontWeight.Medium),
    Font(R.font.ys_display_bold, FontWeight.Bold),
    Font(R.font.ys_display_heavy, FontWeight.Black),
)

private val YSText = FontFamily(
    Font(R.font.ys_text_light, FontWeight.Light),
    Font(R.font.ys_text_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.ys_text_regular, FontWeight.Normal),
    Font(R.font.ys_text_regular_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.ys_text_medium, FontWeight.Medium),
    Font(R.font.ys_text_medium_italic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.ys_text_bold, FontWeight.Bold),
    Font(R.font.ys_text_bold_italic, FontWeight.Bold, FontStyle.Italic),
)

private val DefaultTypography = Typography()

val AppTypography = Typography(
    displayLarge = DefaultTypography.displayLarge.copy(fontFamily = YSDisplay),
    displayMedium = DefaultTypography.displayMedium.copy(fontFamily = YSDisplay),
    displaySmall = DefaultTypography.displaySmall.copy(fontFamily = YSDisplay),
    headlineLarge = DefaultTypography.headlineLarge.copy(fontFamily = YSDisplay),
    headlineMedium = DefaultTypography.headlineMedium.copy(fontFamily = YSDisplay),
    headlineSmall = DefaultTypography.headlineSmall.copy(fontFamily = YSDisplay),
    titleLarge = DefaultTypography.titleLarge.copy(fontFamily = YSDisplay),
    titleMedium = DefaultTypography.titleMedium.copy(fontFamily = YSDisplay),
    titleSmall = DefaultTypography.titleSmall.copy(fontFamily = YSDisplay),
    bodyLarge = DefaultTypography.bodyLarge.copy(fontFamily = YSText),
    bodyMedium = DefaultTypography.bodyMedium.copy(fontFamily = YSText),
    bodySmall = DefaultTypography.bodySmall.copy(fontFamily = YSText),
    labelLarge = DefaultTypography.labelLarge.copy(fontFamily = YSText),
    labelMedium = DefaultTypography.labelMedium.copy(fontFamily = YSText),
    labelSmall = DefaultTypography.labelSmall.copy(fontFamily = YSText),
)

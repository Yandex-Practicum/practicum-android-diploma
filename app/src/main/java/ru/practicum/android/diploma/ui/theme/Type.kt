package ru.practicum.android.diploma.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.practicum.android.diploma.R

val AppFontFamily = FontFamily(
    Font(R.font.ys_display_bold, FontWeight.W700),
    Font(R.font.ys_display_medium, FontWeight.W500),
    Font(R.font.ys_display_regular, FontWeight.W400)
)
val Bold32 = TextStyle(
    fontSize = 32.sp,
    fontFamily = AppFontFamily,
    letterSpacing = 0.sp,
    lineHeight = 38.sp,
    fontWeight = FontWeight.W700
)
val Medium22 = TextStyle(
    fontSize = 22.sp,
    fontFamily = AppFontFamily,
    letterSpacing = 0.sp,
    lineHeight = 26.sp,
    fontWeight = FontWeight.W500
)
val Medium16 = TextStyle(
    fontSize = 16.sp,
    fontFamily = AppFontFamily,
    letterSpacing = 0.sp,
    lineHeight = 19.sp,
    fontWeight = FontWeight.W500
)
val Regular16 = TextStyle(
    fontSize = 16.sp,
    fontFamily = AppFontFamily,
    letterSpacing = 0.sp,
    lineHeight = 19.sp,
    fontWeight = FontWeight.W400
)
val Regular12 = TextStyle(
    fontSize = 12.sp,
    fontFamily = AppFontFamily,
    letterSpacing = 0.sp,
    lineHeight = 16.sp,
    fontWeight = FontWeight.W400
)

val AppTypography = Typography(
    headlineLarge = Bold32,
    headlineMedium = Medium22,
    titleLarge = Medium22,
    titleMedium = Regular16,
    bodyMedium = Regular16,
    bodySmall = Regular12,
    labelLarge = Medium16,
    labelMedium = Regular16,
    labelSmall = Regular12,
)

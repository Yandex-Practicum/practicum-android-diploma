package ru.practicum.android.diploma.ui.team.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object TeamColors {
    val ScreenBackgroundTop = Color(0xFFFAFAFF)
    val ScreenBackgroundBottom = Color(0xFFF3F0FF)
    val ScreenBackgroundSolid = Color(0xFFF8F7FF)
    val PrimaryText = Color(0xFF1F2937)
    val SecondaryText = Color(0xFF6B7280)
    val Accent = Color(0xFF6C63FF)
    val PrimaryBlue = Color(0xFF3772E7)
    val AvatarBackground = Color(0xFFF3F4F6)
    val CardBackground = Color(0xFFFFFFFF)
    val InfoCardBackground = Color(0xFFF1EEFF)

    val CaptainRole = Color(0xFF7C3AED)
    val CaptainRoleBackground = Color(0xFFEDE9FE)
    val MateRole = Color(0xFF2563EB)
    val MateRoleBackground = Color(0xFFDBEAFE)
    val BoatswainRole = Color(0xFF16A34A)
    val BoatswainRoleBackground = Color(0xFFDCFCE7)
    val CookRole = Color(0xFFEA580C)
    val CookRoleBackground = Color(0xFFFFEDD5)

    val BottomNavInactive = Color(0xFF9CA3AF)

    val screenBackgroundBrush: Brush = Brush.verticalGradient(
        colors = listOf(ScreenBackgroundTop, ScreenBackgroundBottom),
    )
}

package ru.practicum.android.diploma.ui.team.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

private const val HEX_SCREEN_BACKGROUND_TOP = 0xFFFAFAFF
private const val HEX_SCREEN_BACKGROUND_BOTTOM = 0xFFF3F0FF
private const val HEX_SCREEN_BACKGROUND_SOLID = 0xFFF8F7FF
private const val HEX_PRIMARY_TEXT = 0xFF1F2937
private const val HEX_SECONDARY_TEXT = 0xFF6B7280
private const val HEX_ACCENT = 0xFF6C63FF
private const val HEX_PRIMARY_BLUE = 0xFF3772E7
private const val HEX_AVATAR_BACKGROUND = 0xFFF3F4F6
private const val HEX_CARD_BACKGROUND = 0xFFFFFFFF
private const val HEX_INFO_CARD_BACKGROUND = 0xFFF1EEFF
private const val HEX_CAPTAIN_ROLE = 0xFF7C3AED
private const val HEX_CAPTAIN_ROLE_BACKGROUND = 0xFFEDE9FE
private const val HEX_MATE_ROLE = 0xFF2563EB
private const val HEX_MATE_ROLE_BACKGROUND = 0xFFDBEAFE
private const val HEX_BOATSWAIN_ROLE = 0xFF16A34A
private const val HEX_BOATSWAIN_ROLE_BACKGROUND = 0xFFDCFCE7
private const val HEX_COOK_ROLE = 0xFFEA580C
private const val HEX_COOK_ROLE_BACKGROUND = 0xFFFFEDD5
private const val HEX_BOTTOM_NAV_INACTIVE = 0xFF9CA3AF

object TeamColors {
    val ScreenBackgroundTop = Color(HEX_SCREEN_BACKGROUND_TOP)
    val ScreenBackgroundBottom = Color(HEX_SCREEN_BACKGROUND_BOTTOM)
    val ScreenBackgroundSolid = Color(HEX_SCREEN_BACKGROUND_SOLID)
    val PrimaryText = Color(HEX_PRIMARY_TEXT)
    val SecondaryText = Color(HEX_SECONDARY_TEXT)
    val Accent = Color(HEX_ACCENT)
    val PrimaryBlue = Color(HEX_PRIMARY_BLUE)
    val AvatarBackground = Color(HEX_AVATAR_BACKGROUND)
    val CardBackground = Color(HEX_CARD_BACKGROUND)
    val InfoCardBackground = Color(HEX_INFO_CARD_BACKGROUND)

    val CaptainRole = Color(HEX_CAPTAIN_ROLE)
    val CaptainRoleBackground = Color(HEX_CAPTAIN_ROLE_BACKGROUND)
    val MateRole = Color(HEX_MATE_ROLE)
    val MateRoleBackground = Color(HEX_MATE_ROLE_BACKGROUND)
    val BoatswainRole = Color(HEX_BOATSWAIN_ROLE)
    val BoatswainRoleBackground = Color(HEX_BOATSWAIN_ROLE_BACKGROUND)
    val CookRole = Color(HEX_COOK_ROLE)
    val CookRoleBackground = Color(HEX_COOK_ROLE_BACKGROUND)

    val BottomNavInactive = Color(HEX_BOTTOM_NAV_INACTIVE)

    val screenBackgroundBrush: Brush = Brush.verticalGradient(
        colors = listOf(ScreenBackgroundTop, ScreenBackgroundBottom),
    )
}

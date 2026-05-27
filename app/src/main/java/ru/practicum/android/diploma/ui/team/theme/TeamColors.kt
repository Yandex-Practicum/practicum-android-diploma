package ru.practicum.android.diploma.ui.team.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

private const val HEX_LIGHT_SCREEN_BACKGROUND_TOP = 0xFFFAFAFF
private const val HEX_LIGHT_SCREEN_BACKGROUND_BOTTOM = 0xFFF3F0FF
private const val HEX_LIGHT_SCREEN_BACKGROUND_SOLID = 0xFFF8F7FF
private const val HEX_LIGHT_PRIMARY_TEXT = 0xFF1F2937
private const val HEX_LIGHT_SECONDARY_TEXT = 0xFF6B7280
private const val HEX_LIGHT_ACCENT = 0xFF6C63FF
private const val HEX_LIGHT_PRIMARY_BLUE = 0xFF3772E7
private const val HEX_LIGHT_AVATAR_BACKGROUND = 0xFFF3F4F6
private const val HEX_LIGHT_CARD_BACKGROUND = 0xFFFFFFFF
private const val HEX_LIGHT_INFO_CARD_BACKGROUND = 0xFFF1EEFF
private const val HEX_LIGHT_CAPTAIN_ROLE = 0xFF7C3AED
private const val HEX_LIGHT_CAPTAIN_ROLE_BACKGROUND = 0xFFEDE9FE
private const val HEX_LIGHT_MATE_ROLE = 0xFF2563EB
private const val HEX_LIGHT_MATE_ROLE_BACKGROUND = 0xFFDBEAFE
private const val HEX_LIGHT_BOATSWAIN_ROLE = 0xFF16A34A
private const val HEX_LIGHT_BOATSWAIN_ROLE_BACKGROUND = 0xFFDCFCE7
private const val HEX_LIGHT_COOK_ROLE = 0xFFEA580C
private const val HEX_LIGHT_COOK_ROLE_BACKGROUND = 0xFFFFEDD5
private const val HEX_LIGHT_BOTTOM_NAV_INACTIVE = 0xFF9CA3AF

private const val HEX_DARK_SCREEN_BACKGROUND_TOP = 0xFF1A1B22
private const val HEX_DARK_SCREEN_BACKGROUND_BOTTOM = 0xFF252833
private const val HEX_DARK_SCREEN_BACKGROUND_SOLID = 0xFF1A1B22
private const val HEX_DARK_PRIMARY_TEXT = 0xFFFDFDFD
private const val HEX_DARK_SECONDARY_TEXT = 0xFFAEAFB4
private const val HEX_DARK_ACCENT = 0xFF6C63FF
private const val HEX_DARK_PRIMARY_BLUE = 0xFF3772E7
private const val HEX_DARK_AVATAR_BACKGROUND = 0xFF3A3B44
private const val HEX_DARK_CARD_BACKGROUND = 0xFF2E3038
private const val HEX_DARK_INFO_CARD_BACKGROUND = 0xFF2A2845
private const val HEX_DARK_CAPTAIN_ROLE = 0xFFC4B5FD
private const val HEX_DARK_CAPTAIN_ROLE_BACKGROUND = 0xFF3B2D5C
private const val HEX_DARK_MATE_ROLE = 0xFF93C5FD
private const val HEX_DARK_MATE_ROLE_BACKGROUND = 0xFF1E3A5F
private const val HEX_DARK_BOATSWAIN_ROLE = 0xFF86EFAC
private const val HEX_DARK_BOATSWAIN_ROLE_BACKGROUND = 0xFF1F3D2E
private const val HEX_DARK_COOK_ROLE = 0xFFFDBA74
private const val HEX_DARK_COOK_ROLE_BACKGROUND = 0xFF4A2C17
private const val HEX_DARK_BOTTOM_NAV_INACTIVE = 0xFF9CA3AF

private data class TeamPalette(
    val screenBackgroundTop: Color,
    val screenBackgroundBottom: Color,
    val screenBackgroundSolid: Color,
    val primaryText: Color,
    val secondaryText: Color,
    val accent: Color,
    val primaryBlue: Color,
    val avatarBackground: Color,
    val cardBackground: Color,
    val infoCardBackground: Color,
    val captainRole: Color,
    val captainRoleBackground: Color,
    val mateRole: Color,
    val mateRoleBackground: Color,
    val boatswainRole: Color,
    val boatswainRoleBackground: Color,
    val cookRole: Color,
    val cookRoleBackground: Color,
    val bottomNavInactive: Color,
) {
    val screenBackgroundBrush: Brush = Brush.verticalGradient(
        colors = listOf(screenBackgroundTop, screenBackgroundBottom),
    )
}

private val LightPalette = TeamPalette(
    screenBackgroundTop = Color(HEX_LIGHT_SCREEN_BACKGROUND_TOP),
    screenBackgroundBottom = Color(HEX_LIGHT_SCREEN_BACKGROUND_BOTTOM),
    screenBackgroundSolid = Color(HEX_LIGHT_SCREEN_BACKGROUND_SOLID),
    primaryText = Color(HEX_LIGHT_PRIMARY_TEXT),
    secondaryText = Color(HEX_LIGHT_SECONDARY_TEXT),
    accent = Color(HEX_LIGHT_ACCENT),
    primaryBlue = Color(HEX_LIGHT_PRIMARY_BLUE),
    avatarBackground = Color(HEX_LIGHT_AVATAR_BACKGROUND),
    cardBackground = Color(HEX_LIGHT_CARD_BACKGROUND),
    infoCardBackground = Color(HEX_LIGHT_INFO_CARD_BACKGROUND),
    captainRole = Color(HEX_LIGHT_CAPTAIN_ROLE),
    captainRoleBackground = Color(HEX_LIGHT_CAPTAIN_ROLE_BACKGROUND),
    mateRole = Color(HEX_LIGHT_MATE_ROLE),
    mateRoleBackground = Color(HEX_LIGHT_MATE_ROLE_BACKGROUND),
    boatswainRole = Color(HEX_LIGHT_BOATSWAIN_ROLE),
    boatswainRoleBackground = Color(HEX_LIGHT_BOATSWAIN_ROLE_BACKGROUND),
    cookRole = Color(HEX_LIGHT_COOK_ROLE),
    cookRoleBackground = Color(HEX_LIGHT_COOK_ROLE_BACKGROUND),
    bottomNavInactive = Color(HEX_LIGHT_BOTTOM_NAV_INACTIVE),
)

private val DarkPalette = TeamPalette(
    screenBackgroundTop = Color(HEX_DARK_SCREEN_BACKGROUND_TOP),
    screenBackgroundBottom = Color(HEX_DARK_SCREEN_BACKGROUND_BOTTOM),
    screenBackgroundSolid = Color(HEX_DARK_SCREEN_BACKGROUND_SOLID),
    primaryText = Color(HEX_DARK_PRIMARY_TEXT),
    secondaryText = Color(HEX_DARK_SECONDARY_TEXT),
    accent = Color(HEX_DARK_ACCENT),
    primaryBlue = Color(HEX_DARK_PRIMARY_BLUE),
    avatarBackground = Color(HEX_DARK_AVATAR_BACKGROUND),
    cardBackground = Color(HEX_DARK_CARD_BACKGROUND),
    infoCardBackground = Color(HEX_DARK_INFO_CARD_BACKGROUND),
    captainRole = Color(HEX_DARK_CAPTAIN_ROLE),
    captainRoleBackground = Color(HEX_DARK_CAPTAIN_ROLE_BACKGROUND),
    mateRole = Color(HEX_DARK_MATE_ROLE),
    mateRoleBackground = Color(HEX_DARK_MATE_ROLE_BACKGROUND),
    boatswainRole = Color(HEX_DARK_BOATSWAIN_ROLE),
    boatswainRoleBackground = Color(HEX_DARK_BOATSWAIN_ROLE_BACKGROUND),
    cookRole = Color(HEX_DARK_COOK_ROLE),
    cookRoleBackground = Color(HEX_DARK_COOK_ROLE_BACKGROUND),
    bottomNavInactive = Color(HEX_DARK_BOTTOM_NAV_INACTIVE),
)

@Composable
@ReadOnlyComposable
private fun teamPalette(): TeamPalette {
    return if (isSystemInDarkTheme()) DarkPalette else LightPalette
}

object TeamColors {
    val ScreenBackgroundTop: Color
        @Composable
        @ReadOnlyComposable
        get() = teamPalette().screenBackgroundTop

    val ScreenBackgroundBottom: Color
        @Composable
        @ReadOnlyComposable
        get() = teamPalette().screenBackgroundBottom

    val ScreenBackgroundSolid: Color
        @Composable
        @ReadOnlyComposable
        get() = teamPalette().screenBackgroundSolid

    val PrimaryText: Color
        @Composable
        @ReadOnlyComposable
        get() = teamPalette().primaryText

    val SecondaryText: Color
        @Composable
        @ReadOnlyComposable
        get() = teamPalette().secondaryText

    val Accent: Color
        @Composable
        @ReadOnlyComposable
        get() = teamPalette().accent

    val PrimaryBlue: Color
        @Composable
        @ReadOnlyComposable
        get() = teamPalette().primaryBlue

    val AvatarBackground: Color
        @Composable
        @ReadOnlyComposable
        get() = teamPalette().avatarBackground

    val CardBackground: Color
        @Composable
        @ReadOnlyComposable
        get() = teamPalette().cardBackground

    val InfoCardBackground: Color
        @Composable
        @ReadOnlyComposable
        get() = teamPalette().infoCardBackground

    val CaptainRole: Color
        @Composable
        @ReadOnlyComposable
        get() = teamPalette().captainRole

    val CaptainRoleBackground: Color
        @Composable
        @ReadOnlyComposable
        get() = teamPalette().captainRoleBackground

    val MateRole: Color
        @Composable
        @ReadOnlyComposable
        get() = teamPalette().mateRole

    val MateRoleBackground: Color
        @Composable
        @ReadOnlyComposable
        get() = teamPalette().mateRoleBackground

    val BoatswainRole: Color
        @Composable
        @ReadOnlyComposable
        get() = teamPalette().boatswainRole

    val BoatswainRoleBackground: Color
        @Composable
        @ReadOnlyComposable
        get() = teamPalette().boatswainRoleBackground

    val CookRole: Color
        @Composable
        @ReadOnlyComposable
        get() = teamPalette().cookRole

    val CookRoleBackground: Color
        @Composable
        @ReadOnlyComposable
        get() = teamPalette().cookRoleBackground

    val BottomNavInactive: Color
        @Composable
        @ReadOnlyComposable
        get() = teamPalette().bottomNavInactive

    val screenBackgroundBrush: Brush
        @Composable
        @ReadOnlyComposable
        get() = teamPalette().screenBackgroundBrush
}

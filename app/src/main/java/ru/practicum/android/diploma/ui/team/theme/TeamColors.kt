package ru.practicum.android.diploma.ui.team.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

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
    screenBackgroundTop = Color(0xFFFAFAFF),
    screenBackgroundBottom = Color(0xFFF3F0FF),
    screenBackgroundSolid = Color(0xFFF8F7FF),
    primaryText = Color(0xFF1F2937),
    secondaryText = Color(0xFF6B7280),
    accent = Color(0xFF6C63FF),
    primaryBlue = Color(0xFF3772E7),
    avatarBackground = Color(0xFFF3F4F6),
    cardBackground = Color(0xFFFFFFFF),
    infoCardBackground = Color(0xFFF1EEFF),
    captainRole = Color(0xFF7C3AED),
    captainRoleBackground = Color(0xFFEDE9FE),
    mateRole = Color(0xFF2563EB),
    mateRoleBackground = Color(0xFFDBEAFE),
    boatswainRole = Color(0xFF16A34A),
    boatswainRoleBackground = Color(0xFFDCFCE7),
    cookRole = Color(0xFFEA580C),
    cookRoleBackground = Color(0xFFFFEDD5),
    bottomNavInactive = Color(0xFF9CA3AF),
)

private val DarkPalette = TeamPalette(
    screenBackgroundTop = Color(0xFF1A1B22),
    screenBackgroundBottom = Color(0xFF252833),
    screenBackgroundSolid = Color(0xFF1A1B22),
    primaryText = Color(0xFFFDFDFD),
    secondaryText = Color(0xFFAEAFB4),
    accent = Color(0xFF6C63FF),
    primaryBlue = Color(0xFF3772E7),
    avatarBackground = Color(0xFF3A3B44),
    cardBackground = Color(0xFF2E3038),
    infoCardBackground = Color(0xFF2A2845),
    captainRole = Color(0xFFC4B5FD),
    captainRoleBackground = Color(0xFF3B2D5C),
    mateRole = Color(0xFF93C5FD),
    mateRoleBackground = Color(0xFF1E3A5F),
    boatswainRole = Color(0xFF86EFAC),
    boatswainRoleBackground = Color(0xFF1F3D2E),
    cookRole = Color(0xFFFDBA74),
    cookRoleBackground = Color(0xFF4A2C17),
    bottomNavInactive = Color(0xFF9CA3AF),
)

@Composable
@ReadOnlyComposable
private fun teamPalette(): TeamPalette {
    return if (isSystemInDarkTheme()) DarkPalette else LightPalette
}

object TeamColors {
    val ScreenBackgroundTop: Color
        @Composable @ReadOnlyComposable get() = teamPalette().screenBackgroundTop

    val ScreenBackgroundBottom: Color
        @Composable @ReadOnlyComposable get() = teamPalette().screenBackgroundBottom

    val ScreenBackgroundSolid: Color
        @Composable @ReadOnlyComposable get() = teamPalette().screenBackgroundSolid

    val PrimaryText: Color
        @Composable @ReadOnlyComposable get() = teamPalette().primaryText

    val SecondaryText: Color
        @Composable @ReadOnlyComposable get() = teamPalette().secondaryText

    val Accent: Color
        @Composable @ReadOnlyComposable get() = teamPalette().accent

    val PrimaryBlue: Color
        @Composable @ReadOnlyComposable get() = teamPalette().primaryBlue

    val AvatarBackground: Color
        @Composable @ReadOnlyComposable get() = teamPalette().avatarBackground

    val CardBackground: Color
        @Composable @ReadOnlyComposable get() = teamPalette().cardBackground

    val InfoCardBackground: Color
        @Composable @ReadOnlyComposable get() = teamPalette().infoCardBackground

    val CaptainRole: Color
        @Composable @ReadOnlyComposable get() = teamPalette().captainRole

    val CaptainRoleBackground: Color
        @Composable @ReadOnlyComposable get() = teamPalette().captainRoleBackground

    val MateRole: Color
        @Composable @ReadOnlyComposable get() = teamPalette().mateRole

    val MateRoleBackground: Color
        @Composable @ReadOnlyComposable get() = teamPalette().mateRoleBackground

    val BoatswainRole: Color
        @Composable @ReadOnlyComposable get() = teamPalette().boatswainRole

    val BoatswainRoleBackground: Color
        @Composable @ReadOnlyComposable get() = teamPalette().boatswainRoleBackground

    val CookRole: Color
        @Composable @ReadOnlyComposable get() = teamPalette().cookRole

    val CookRoleBackground: Color
        @Composable @ReadOnlyComposable get() = teamPalette().cookRoleBackground

    val BottomNavInactive: Color
        @Composable @ReadOnlyComposable get() = teamPalette().bottomNavInactive

    val screenBackgroundBrush: Brush
        @Composable @ReadOnlyComposable get() = teamPalette().screenBackgroundBrush
}

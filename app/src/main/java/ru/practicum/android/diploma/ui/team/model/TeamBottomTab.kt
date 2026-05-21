package ru.practicum.android.diploma.ui.team.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.practicum.android.diploma.R

enum class TeamBottomTab(
    @StringRes val titleResId: Int,
    @DrawableRes val iconResId: Int,
) {
    Main(R.string.main, R.drawable.ic_main_24),
    Favorites(R.string.favorites, R.drawable.ic_favorites_24),
    Team(R.string.team, R.drawable.ic_team_24),
}

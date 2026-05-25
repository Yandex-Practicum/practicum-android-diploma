package ru.practicum.android.diploma.core.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(val route: String) {
    @Serializable object Search : Screen("search")
    @Serializable object Filter : Screen("filters")
    @Serializable object Area : Screen("area")
    @Serializable object Country : Screen("country")
    @Serializable object Region : Screen("region")
    @Serializable object Industry : Screen("industry")
    @Serializable data class Vacancy(val id: String) : Screen("vacancy/{id}")
    @Serializable object Favorites : Screen("favorites")
    @Serializable object Team : Screen("team")
}

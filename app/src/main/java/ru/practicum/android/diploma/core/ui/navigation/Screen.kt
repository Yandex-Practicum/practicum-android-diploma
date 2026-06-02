package ru.practicum.android.diploma.core.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(val route: String) {
    @Serializable object Search : Screen("search")
    @Serializable object Filter : Screen("filters")
    @Serializable object Area : Screen("area")
    @Serializable object Country : Screen("country")
    @Serializable data class Region(val countryId: String?) : Screen("region/{countryId}")
    @Serializable data class Industry(val industryId: String?) : Screen("industry/{industryId}")
    @Serializable data class Vacancy(val id: String) : Screen("vacancy/{id}")
    @Serializable object Favorites : Screen("favorites")
    @Serializable object Team : Screen("team")
}

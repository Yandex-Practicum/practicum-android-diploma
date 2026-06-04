package ru.practicum.android.diploma.presentation.filter

import ru.practicum.android.diploma.domain.models.FilterSettings

data class FilterUiState(
    val settings: FilterSettings = FilterSettings(),
) {
    val hasActiveFilters: Boolean
        get() = settings.hasActiveFilters

    val workplaceTitle: String?
        get() = settings.regionName ?: settings.countryName

    val industryTitle: String?
        get() = settings.industryName
}

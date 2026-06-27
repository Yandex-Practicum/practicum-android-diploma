package ru.practicum.android.diploma.presentation.viewmodels

import ru.practicum.android.diploma.domain.models.FilterSettings

sealed interface FiltersState {
    data object Loading : FiltersState
    data class Content(
        val settings: FilterSettings,
        val canApply: Boolean,
        val canReset: Boolean
    ) : FiltersState
}

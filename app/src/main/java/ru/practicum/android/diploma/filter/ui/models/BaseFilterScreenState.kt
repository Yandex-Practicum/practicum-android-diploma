package ru.practicum.android.diploma.filter.ui.models

import ru.practicum.android.diploma.filter.domain.models.SelectedFilter

sealed interface BaseFilterScreenState {
    object Empty : BaseFilterScreenState
    data class Content(val selectedFilter: SelectedFilter) : BaseFilterScreenState
}
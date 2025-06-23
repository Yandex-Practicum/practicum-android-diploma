package ru.practicum.android.diploma.ui.filter.model

sealed interface FilterScreenState {
    data class CONTENT(val value: SelectedFilters) : FilterScreenState
}

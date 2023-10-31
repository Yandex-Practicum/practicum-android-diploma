package ru.practicum.android.diploma.filter.presentation.models

sealed interface FilterScreenState {
    object Empty: FilterScreenState

    data class Content(val data: Any): FilterScreenState
}
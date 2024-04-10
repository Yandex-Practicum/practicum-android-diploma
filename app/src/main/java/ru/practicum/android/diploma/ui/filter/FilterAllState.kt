package ru.practicum.android.diploma.ui.filter

sealed interface FilterAllState {

    data object Content: FilterAllState

    data object Empty: FilterAllState
}

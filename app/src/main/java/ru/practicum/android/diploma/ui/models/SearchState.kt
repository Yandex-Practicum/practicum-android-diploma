package ru.practicum.android.diploma.ui.models

sealed class SearchState{
    data object Idle:SearchState()
    data object Nothing:SearchState()
    data object Loading:SearchState()
    data object Complete:SearchState()
    data object Error:SearchState()
}

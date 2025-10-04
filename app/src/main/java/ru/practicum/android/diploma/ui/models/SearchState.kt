package ru.practicum.android.diploma.ui.models

sealed class SearchState {
    data object Idle : SearchState()
    data object Nothing : SearchState()
    data object Loading : SearchState()
    data class Complete(val data: List<Int>) : SearchState()
    data class Error(val message: String) : SearchState()
}

package ru.practicum.android.diploma.root.model

sealed interface UiState {
    object Loading : UiState
    data class Content(val list: List<Any?>) : UiState
    data class NoData(val message: Int) : UiState
    data class Offline(val message: Int) : UiState
    data class Error(val message: Int) : UiState
}
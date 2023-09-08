package ru.practicum.android.diploma.root.model

sealed interface UiState {

    object Loading : UiState
    data class Content<T>(val list: List<T>) : UiState
    data class NoData(val message: String) : UiState
    data class Offline(val message: String) : UiState
    data class Error(val message: String) : UiState


}
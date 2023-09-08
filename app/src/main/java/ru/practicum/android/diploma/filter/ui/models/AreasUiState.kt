package ru.practicum.android.diploma.filter.ui.models

sealed interface AreasUiState {

    object Loading : AreasUiState
    data class Content<T>(val list: List<T>) : AreasUiState
    data class NoData(val message: String) : AreasUiState
    data class Offline(val message: String) : AreasUiState
    data class Error(val message: String) : AreasUiState


}
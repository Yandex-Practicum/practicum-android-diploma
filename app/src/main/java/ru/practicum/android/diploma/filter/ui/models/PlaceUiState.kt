package ru.practicum.android.diploma.filter.ui.models

sealed interface PlaceUiState {
    object Default : PlaceUiState
    object Country : PlaceUiState
    object Region : PlaceUiState
}
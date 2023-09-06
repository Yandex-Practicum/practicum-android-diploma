package ru.practicum.android.diploma.filter.ui.models

sealed interface FilterUiState {

    object Default : FilterUiState
    object Country : FilterUiState
    object Region : FilterUiState
    object FullData : FilterUiState
    object Empty : FilterUiState
}
package ru.practicum.android.diploma.search.ui.models

import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.functional.Failure

sealed interface SearchUiState {
    object Default : SearchUiState
    object Loading : SearchUiState
    data class Content(
        val list: List<Vacancy>,
        val found: Int,
        val isLastPage: Boolean
    ) : SearchUiState
    data class AddedContent(
        val list: List<Vacancy>,
        val found: Int,
        val isLastPage: Boolean
    ) : SearchUiState
    data class Error(val error: Failure) : SearchUiState
    data class ErrorScrollLoading(val error: Failure) : SearchUiState
}
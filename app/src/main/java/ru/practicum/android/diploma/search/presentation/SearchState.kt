package ru.practicum.android.diploma.search.presentation

import ru.practicum.android.diploma.core.domain.model.SearchVacanciesResult

sealed interface SearchState {
    object Default : SearchState
    object Loading : SearchState
    data class Content(val data: SearchVacanciesResult?) : SearchState
    object NetworkError : SearchState
    object EmptyResult : SearchState
}

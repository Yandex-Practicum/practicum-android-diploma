package ru.practicum.android.diploma.search.presentation

import ru.practicum.android.diploma.core.domain.model.SearchVacanciesResult
import ru.practicum.android.diploma.core.domain.model.ShortVacancy

sealed interface SearchState {
    data object Default : SearchState
    data object Loading : SearchState
    data class Content(val data: SearchVacanciesResult?) : SearchState
    data object NetworkError : SearchState
    data object EmptyResult : SearchState
    data object ServerError : SearchState
    data class Pagination(val data: List<ShortVacancy>, val error: SearchState? = null) : SearchState
}

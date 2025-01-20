package ru.practicum.android.diploma.search.domain.model

sealed interface SearchViewState {
    data class Success(val vacancyList: List<VacancyList>) : SearchViewState
    data object NotFoundError : SearchViewState
    data object ServerError : SearchViewState
    data object ConnectionError : SearchViewState
    data object Loading : SearchViewState
}

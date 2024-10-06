package ru.practicum.android.diploma.search.presentation

sealed class VacancySearchScreenState {
    data object EmptyScreen : VacancySearchScreenState()
    data object Loading : VacancySearchScreenState()
    data object NetworkError : VacancySearchScreenState()
    data object ServerError : VacancySearchScreenState()
    data object SearchError : VacancySearchScreenState()
    data object Content : VacancySearchScreenState()
    data object PaginationLoading : VacancySearchScreenState()
    data class PaginationError(val message: String) : VacancySearchScreenState()
}

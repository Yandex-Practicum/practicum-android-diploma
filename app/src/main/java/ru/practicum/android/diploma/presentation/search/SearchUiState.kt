package ru.practicum.android.diploma.presentation.search

import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancySearchRequest

enum class SearchError {
    NO_INTERNET,
    SERVER_ERROR,
    EMPTY,
}

data class SearchUiState(
    val query: String = "",
    val isLoading: Boolean = false,
    val found: Int = 0,
    val vacancies: List<Vacancy> = emptyList(),
    val errorType: SearchError? = null,
    val currentPage: Int = VacancySearchRequest.FIRST_PAGE,
    val totalPages: Int = 0,
    val isNextPageLoading: Boolean = false,
)

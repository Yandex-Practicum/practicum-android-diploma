package ru.practicum.android.diploma.presentation.search

import ru.practicum.android.diploma.domain.models.Vacancy

data class SearchUiState(
    val query: String = "",
    val isLoading: Boolean = false,
    val found: Int = 0,
    val vacancies: List<Vacancy> = emptyList(),
)

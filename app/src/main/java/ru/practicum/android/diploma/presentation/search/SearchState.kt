package ru.practicum.android.diploma.presentation.search

import ru.practicum.android.diploma.domain.models.main.VacancyShort

data class SearchState(
    val isLoading: Boolean = false,
    val content: List<VacancyShort>? = null,
    val error: UiError? = null,
    val query: String = "",
    val showResultText: Boolean = false,
    val resultText: String = "",
    val currentPage: Int = 0,
    val totalPages: Int = 0,
    val isNextPageLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isInitialLoading: Boolean = false
)

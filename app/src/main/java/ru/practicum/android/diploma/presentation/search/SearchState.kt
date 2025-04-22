package ru.practicum.android.diploma.presentation.search

import ru.practicum.android.diploma.domain.models.main.VacancyShort

data class SearchState(
    val isLoading: Boolean = false,
    val content: List<VacancyShort>? = null,
    val error: UiError? = null,
    val query: String = "",
    val resultCount: Int? = 0,
    val showResultText: Boolean = false,
    val resultText: String = ""
)

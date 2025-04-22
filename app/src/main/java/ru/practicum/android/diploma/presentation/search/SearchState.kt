package ru.practicum.android.diploma.presentation.search

data class SearchState<T>(
    val isLoading: Boolean = false,
    val noContent: Boolean = true,
    val content: T? = null,
    val error: UiError? = null,
    val query: String = "",
    val resultCount: Int? = 0,
    val showResultText: Boolean = false,
    val resultText: String = ""
)

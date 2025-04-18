package ru.practicum.android.diploma.presentation.search

data class SearchState<T>(
    val isLoading: Boolean = false,
    val content: T? = null,
    val error: UiError? = null,
    val query: String = ""
)

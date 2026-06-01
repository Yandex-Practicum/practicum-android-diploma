package ru.practicum.android.diploma.core.domain.models

data class SearchResult(
    val items: List<Vacancy>,
    val found: Int,
    val page: Int,
    val pages: Int
)

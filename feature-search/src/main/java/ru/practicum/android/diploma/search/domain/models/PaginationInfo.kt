package ru.practicum.android.diploma.search.domain.models

internal data class PaginationInfo(
    val items: List<Vacancy>,
    val page: Int,
    val pages: Int,
    val found: Int,
)

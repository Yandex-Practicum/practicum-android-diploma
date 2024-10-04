package ru.practicum.android.diploma.search.domain.models

data class PaginationInfo(
    val items: List<Vacancy>,
    val page: Int,
    val pages: Int,
    val found: Int,
)

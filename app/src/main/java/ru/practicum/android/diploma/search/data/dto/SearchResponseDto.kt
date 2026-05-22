package ru.practicum.android.diploma.search.data.dto

data class SearchResponseDto (
    val items: List<VacancyDto>,
    val found: Int,
    val page: Int,
    val pages: Int,
    val perPage: Int
)

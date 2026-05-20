package ru.practicum.android.diploma.search.data.dto

data class SearchResponseDto (
    private val items: List<VacancyDto>,
    private val found: Int,
    private val page: Int,
    private val pages: Int,
    private val perPage: Int
)

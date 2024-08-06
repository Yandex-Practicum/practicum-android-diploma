package ru.practicum.android.diploma.data.dto

data class SearchResponse(
    val items: List<VacancyDto>,
    val found: Int,
    val pages: Int,
    val page: Int,
) : Responce()

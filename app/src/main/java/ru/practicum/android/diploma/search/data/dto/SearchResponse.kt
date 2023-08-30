package ru.practicum.android.diploma.search.data.dto

data class SearchResponse(
    val results: List<VacancyDto>,
) : Response()
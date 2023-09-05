package ru.practicum.android.diploma.search.data.dto

import ru.practicum.android.diploma.search.data.dto.models.VacancyDto

data class SearchResponse(
    val items: List<VacancyDto>,
    val found: Int,
    val page: Int,
    val pages: Int,
    val per_page: Int,
) : Response()
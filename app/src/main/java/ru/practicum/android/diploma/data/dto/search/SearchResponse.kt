package ru.practicum.android.diploma.data.dto.search

import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.VacancyDto

data class SearchResponse(
    val items: List<VacancyDto>,
    val found: Int,
    val page: Int,
    val pages: Int,
    val per_page: Int,
) : Response()
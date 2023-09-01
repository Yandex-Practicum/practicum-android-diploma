package ru.practicum.android.diploma.search.data.network.dto

import kotlinx.serialization.Serializable
import ru.practicum.android.diploma.search.data.network.Response

@Serializable
data class VacanciesSearchResponse(
    val found: Int? = 0,
    val items: List<VacancyDto>? = emptyList(),
    val page: Int? = 0,
    val pages: Int? = 0,
    val per_page: Int? = 0,
) : Response()
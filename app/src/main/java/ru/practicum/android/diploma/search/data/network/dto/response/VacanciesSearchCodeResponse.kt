package ru.practicum.android.diploma.search.data.network.dto.response

import kotlinx.serialization.Serializable
import ru.practicum.android.diploma.search.data.network.CodeResponse
import ru.practicum.android.diploma.search.data.network.dto.VacancyDto

@Serializable
data class VacanciesSearchCodeResponse(
    val found: Int? = 0,
    val items: List<VacancyDto>? = emptyList(),
    val page: Int? = 0,
    val pages: Int? = 0,
    val per_page: Int? = 0,
) : CodeResponse()
package ru.practicum.android.diploma.search.data.network.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.practicum.android.diploma.search.data.network.CodeResponse
import ru.practicum.android.diploma.search.data.network.dto.VacancyDto

@Serializable
data class VacanciesSearchCodeResponse(
    @SerialName("found") val found: Int? = 0,
    @SerialName("items") val items: List<VacancyDto>? = emptyList(),
    @SerialName("page") val page: Int? = 0,
    @SerialName("pages") val pages: Int? = 0,
    @SerialName("perPage") val perPage: Int? = 0,
) : CodeResponse()
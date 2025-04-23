package ru.practicum.android.diploma.data.dto.response

import kotlinx.serialization.SerialName
import ru.practicum.android.diploma.data.dto.main.VacancyShortDto

@Serializable
data class VacancySearchResponseDto(
    val items: List<VacancyShortDto>,
    val found: Int,
    val page: Int,
    val pages: Int,
    @SerialName("per_page") val perPage: Int
)

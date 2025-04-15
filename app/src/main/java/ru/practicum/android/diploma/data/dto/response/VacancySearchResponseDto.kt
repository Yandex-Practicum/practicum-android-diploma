package ru.practicum.android.diploma.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.practicum.android.diploma.data.dto.main.VacancyShortDtoEntity

@Serializable
data class VacancySearchResponseDto(
    val items: List<VacancyShortDtoEntity>,
    val found: Int,
    val page: Int,
    val pages: Int,
    @SerialName("per_page") val perPage: Int
)

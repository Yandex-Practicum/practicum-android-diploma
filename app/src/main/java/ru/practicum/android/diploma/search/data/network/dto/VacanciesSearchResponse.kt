package ru.practicum.android.diploma.search.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VacanciesSearchResponse(
    val found: Int = 0,
   // @SerialName("items")
    val vacancies: List<VacancyDto> = emptyList(),
    val page: Int = 0,
    val pages: Int = 0,
    val per_page: Int = 0,
)
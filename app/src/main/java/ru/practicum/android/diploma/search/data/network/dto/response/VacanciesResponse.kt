package ru.practicum.android.diploma.search.data.network.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.practicum.android.diploma.search.data.network.dto.VacancyDto

//@NewResponse
@Serializable
data class VacanciesResponse(
    @SerialName("found") val found: Int,
    @SerialName("items") val items: List<VacancyDto>,
    @SerialName("page") val page: Int,
    @SerialName("pages") val pages: Int,
    @SerialName("per_page") val per_page: Int,
) {
    companion object {
        val empty = VacanciesResponse(0, emptyList(), 0, 0 , 0)
    }
}
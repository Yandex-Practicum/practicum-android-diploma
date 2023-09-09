package ru.practicum.android.diploma.search.data.network.dto.response

import kotlinx.serialization.Serializable
import ru.practicum.android.diploma.search.data.network.dto.VacancyDto

@Serializable
data class VacanciesResponse(
    val found: Int,
    val items: List<VacancyDto>,
    val page: Int,
    val pages: Int,
    val per_page: Int,
) {

    companion object {
        val empty = VacanciesResponse(0, emptyList(), 0, 0 , 0)
    }
}
package ru.practicum.android.diploma.data.network.dto

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.dto.VacancyDto

data class SearchVacanciesResponse(
    val found: Int? = null,
    val items: ArrayList<VacancyDto> = arrayListOf(),
    val page: Int? = null,
    val pages: Int? = null,
    @SerializedName("per_page")
    val perPage: Int? = null
)

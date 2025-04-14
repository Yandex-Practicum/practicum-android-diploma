package ru.practicum.android.diploma.data.network.dto

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.dto.ApiResponse
import ru.practicum.android.diploma.data.dto.Vacancy

data class SearchVacanciesResponse(
    val found: Int? = null,
    val items: ArrayList<Vacancy> = arrayListOf(),
    val page: Int? = null,
    val pages: Int? = null,
    @SerializedName("per_page")
    val perPage: Int? = null
) : ApiResponse()

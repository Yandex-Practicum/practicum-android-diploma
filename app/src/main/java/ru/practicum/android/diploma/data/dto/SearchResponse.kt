package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    val items: List<VacancyDto>,
    val found: Int,
    val pages: Int,
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
) : Response()

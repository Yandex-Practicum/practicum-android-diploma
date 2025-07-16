package ru.practicum.android.diploma.data.models.vacancies

import com.google.gson.annotations.SerializedName

data class VacanciesResponseDto(
    @SerializedName("found") val found: Int,
    @SerializedName("items") val items: List<VacanciesDto>,
    @SerializedName("page") val page: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("per_page") val perPage: Int
) : Response()

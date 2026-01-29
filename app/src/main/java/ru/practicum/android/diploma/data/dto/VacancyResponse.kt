package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class VacancyResponse(
    val found: Int,
    val pages: Int,
    val page: Int,
    @SerializedName("items") val vacancies: List<VacancyDto>?
) : Response()

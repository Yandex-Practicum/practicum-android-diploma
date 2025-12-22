package ru.practicum.android.diploma.search.data.dto

import com.google.gson.annotations.SerializedName

data class VacancyResponseDto(
    val found: Int,
    val pages: Int,
    val page: Int,
    @SerializedName("items")
    val vacancies: List<VacancyDetailDto>?
)

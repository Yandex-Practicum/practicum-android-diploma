package ru.practicum.android.diploma.data.dto

data class VacanciesResponse(
    val found: Int,
    val pages: Int,
    val page: Int,
    val items: List<VacancyCardDto>
)

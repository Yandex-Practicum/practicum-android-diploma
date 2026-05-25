package ru.practicum.android.diploma.data.dto

data class VacanciesResponseDto(
    val found: Int,
    val pages: Int,
    val page: Int,
    val items: List<VacancyCardDto>
)

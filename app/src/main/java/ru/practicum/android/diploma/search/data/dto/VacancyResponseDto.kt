package ru.practicum.android.diploma.search.data.dto

data class VacancyResponseDto(
    val found: Int,
    val pages: Int,
    val page: Int,
    val vacancies: List<VacancyDetailDto>?
)

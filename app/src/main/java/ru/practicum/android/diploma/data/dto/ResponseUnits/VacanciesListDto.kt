package ru.practicum.android.diploma.data.dto.ResponseUnits

data class VacanciesListDto(
    val vacancyDto: Array<VacancyDto>,
    val found: Int,
    val page: Int,
    val pages: Int,
    val per_page: Int,
)

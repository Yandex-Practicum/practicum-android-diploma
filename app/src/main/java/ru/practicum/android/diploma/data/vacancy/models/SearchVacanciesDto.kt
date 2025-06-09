package ru.practicum.android.diploma.data.vacancy.models

data class SearchVacanciesDto(
    val page: Int,
    val pages: Int,
    val items: List<VacancyDto>,
)

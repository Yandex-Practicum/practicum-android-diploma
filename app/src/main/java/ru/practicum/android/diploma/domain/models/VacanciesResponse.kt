package ru.practicum.android.diploma.domain.models

data class VacanciesResponse(
    val results: List<Vacancy>,
    val foundVacancies: Int,
    val page: Int,
    val pages: Int,
)

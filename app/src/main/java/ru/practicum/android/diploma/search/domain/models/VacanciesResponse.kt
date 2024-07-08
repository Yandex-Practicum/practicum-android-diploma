package ru.practicum.android.diploma.search.domain.models

data class VacanciesResponse(
    val results: List<Vacancy>,
    val foundVacancies: Int,
    val resultCode: Int
)

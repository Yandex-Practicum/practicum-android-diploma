package ru.practicum.android.diploma.search.domain.models

data class VacanciesResponse(
    val results: List<Vacancy>,
    val resultCode: Int
)

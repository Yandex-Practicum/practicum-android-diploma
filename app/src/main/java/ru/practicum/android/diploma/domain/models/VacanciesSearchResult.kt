package ru.practicum.android.diploma.domain.models

data class VacanciesSearchResult(
    val found: Int,
    val page: Int,
    val pages: Int,
    val vacancies: List<Vacancy>,
)

package ru.practicum.android.diploma.search.domain.models

data class VacanciesPage(
    val vacancies: List<Vacancy>,
    val found: Int,
    val page: Int,
    val pages: Int,
)

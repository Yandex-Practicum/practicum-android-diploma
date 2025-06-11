package ru.practicum.android.diploma.domain.vacancy.models

data class VacanciesWithPage(
    val page: Int,
    val pages: Int,
    val items: List<Vacancy>
)

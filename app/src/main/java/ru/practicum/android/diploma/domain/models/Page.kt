package ru.practicum.android.diploma.domain.models

data class Page(
    val vacancies: List<Vacancy>,
    val page: Int,
    val pages: Int,
    val found: Int
)

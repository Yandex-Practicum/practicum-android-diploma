package ru.practicum.android.diploma.search.domain.models

data class Vacancies (
    val found: Int,
    val items: List<Vacancy>,
    val page: Int,
    val pages: Int,
    val perPage: Int,
)
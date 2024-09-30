package ru.practicum.android.diploma.search.domain.models

data class VacancySearchParams(
    val query: String = "",
    val perPage: Int = 20,
    val page: Int,
)

package ru.practicum.android.diploma.domain.models

data class SearchList(
    val found: Int?,
    val maxPages: Int?,
    val currentPages: Int?,
    val listVacancy: List<Vacancy>?
)

package ru.practicum.android.diploma.domain.models

data class VacancySearchFilter(
    val page: Int = 1,
    val text: String? = null
)

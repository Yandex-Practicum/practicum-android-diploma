package ru.practicum.android.diploma.domain.models

data class ReceivedVacanciesData(
    val found: Int? = null,
    val items: List<Vacancy> = arrayListOf(),
    val page: Int? = null,
    val pages: Int? = null,
    val perPage: Int? = null
)

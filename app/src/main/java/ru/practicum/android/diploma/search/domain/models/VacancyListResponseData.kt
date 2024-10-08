package ru.practicum.android.diploma.search.domain.models

data class VacancyListResponseData(
    val items: List<VacancySearch>,
    val found: Int,
    val page: Int,
    val pages: Int
)

package ru.practicum.android.diploma.search.domain.model

data class VacancyResponse(
    val found: Int,
    val pages: Int,
    val page: Int,
    val vacancies: List<VacancyDetail>
)

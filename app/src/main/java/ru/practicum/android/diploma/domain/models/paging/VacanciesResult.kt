package ru.practicum.android.diploma.domain.models.paging

import ru.practicum.android.diploma.domain.models.vacancies.Vacancy

data class VacanciesResult(
    val vacancies: List<Vacancy>,
    val page: Int,
    val pages: Int,
    val totalFound: Int
)

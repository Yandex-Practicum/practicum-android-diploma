package ru.practicum.android.diploma.domain.models

data class VacancySearchResult(
    val totalFound: Int,
    val totalPages: Int,
    val vacancies: List<Vacancy>,
    val errorCode: Int
)

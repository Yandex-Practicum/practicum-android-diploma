package ru.practicum.android.diploma.domain.models

data class VacanciesSearchResult(
    val vacancies: List<Vacancy>,
    val vacanciesFound: Int,
    val pagesCount: Int,
    val currentPage: Int
)

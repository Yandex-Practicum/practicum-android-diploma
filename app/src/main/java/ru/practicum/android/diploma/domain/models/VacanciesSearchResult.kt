package ru.practicum.android.diploma.domain.models

data class VacanciesSearchResult(
    val vacancies: List<VacancyCard>,
    val vacanciesFound: Int,
    val pagesCount: Int,
    val currentPage: Int
)

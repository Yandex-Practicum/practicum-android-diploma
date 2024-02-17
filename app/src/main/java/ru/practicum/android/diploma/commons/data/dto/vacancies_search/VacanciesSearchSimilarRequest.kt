package ru.practicum.android.diploma.commons.data.dto.vacancies_search

data class VacanciesSearchSimilarRequest(
    val id: String,
    val page: Long,
    val amount: Long = 20L
)

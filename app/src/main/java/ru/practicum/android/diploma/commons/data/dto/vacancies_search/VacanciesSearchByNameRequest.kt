package ru.practicum.android.diploma.commons.data.dto.vacancies_search

data class VacanciesSearchByNameRequest(
    val name: String,
    val page: Long,
    val amount: Long = 20L
)

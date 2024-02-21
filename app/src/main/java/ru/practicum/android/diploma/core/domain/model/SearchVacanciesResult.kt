package ru.practicum.android.diploma.core.domain.model

data class SearchVacanciesResult(
    val numOfResults: Int,
    val vacancies: List<ShortVacancy>
)

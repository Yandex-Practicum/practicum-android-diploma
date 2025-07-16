package ru.practicum.android.diploma.data.models.vacancies

data class VacanciesRequest(
    val text: String,
    val page: Int = 0,
    val perPage: Int = 20
)

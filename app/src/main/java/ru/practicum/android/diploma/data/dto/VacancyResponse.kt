package ru.practicum.android.diploma.data.dto

import ru.practicum.android.diploma.data.dto.Vacancy

data class VacancyResponse(
    val items: List<Vacancy>,
    val page: Int,
    val pages: Int,
    val perPage: Int,
    val total: Int
)

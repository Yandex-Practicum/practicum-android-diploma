package ru.practicum.android.diploma.data.dto

data class VacanciesResponse(
    val items: List<VacancyDto>,
    val pages: Int,
    val page: Int,
    val found: Int
) : Response()

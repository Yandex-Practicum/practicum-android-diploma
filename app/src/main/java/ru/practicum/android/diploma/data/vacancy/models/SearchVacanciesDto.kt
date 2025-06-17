package ru.practicum.android.diploma.data.vacancy.models

import ru.practicum.android.diploma.data.Response

data class SearchVacanciesDto(
    val page: Int,
    val pages: Int,
    val found: Int,
    val items: List<VacancyDto>,
) : Response()

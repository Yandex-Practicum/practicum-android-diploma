package ru.practicum.android.diploma.data.dto.vacancy

import ru.practicum.android.diploma.data.dto.Response

data class VacanciesResponse (
    val found: Integer,
    val pages: Integer,
    val page: Integer,
    val items: List<VacancyCardDto>
): Response()

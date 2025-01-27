package ru.practicum.android.diploma.data.dto

class VacanciesResponseDto(
    val found: Int,
    val items: List<VacancyDto>,
    val page: Int,
    val pages: Int
) : Response()

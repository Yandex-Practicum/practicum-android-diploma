package ru.practicum.android.diploma.search.data.dto

import ru.practicum.android.diploma.core.data.dto.VacancyDto

data class SearchResponseDto(
    val items: List<VacancyDto>,
    val found: Int,
    val page: Int,
    val pages: Int,
    val perPage: Int
)

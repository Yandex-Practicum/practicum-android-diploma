package ru.practicum.android.diploma.search.data.dto

import ru.practicum.android.diploma.search.data.dto.Response
import ru.practicum.android.diploma.search.data.dto.VacancyDto

data class SearchResponse(
    val results: List<VacancyDto>,
) : Response()
package ru.practicum.android.diploma.data.dto.similar

import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.VacancyDto

data class SearchSimilarResponse(
    val items: List<VacancyDto>,
    val found: Int,
    val page: Int,
    val pages: Int,
    val per_page: Int,
) : Response()
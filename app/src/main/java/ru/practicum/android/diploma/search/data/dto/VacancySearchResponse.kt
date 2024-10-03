package ru.practicum.android.diploma.search.data.dto

import ru.practicum.android.diploma.util.network.Response

class VacancySearchResponse(
    val items: List<VacancyItemDto>,
    val found: Int,
    val page: Int,
    val pages: Int,
    val perPage: Int,
) : Response()

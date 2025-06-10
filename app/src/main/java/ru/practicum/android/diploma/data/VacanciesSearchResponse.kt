package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.models.VacanyItemDto

class VacanciesSearchResponse(
    val page: Int,
    val pages: Int,
    val items: List<VacanyItemDto>
) : Response()

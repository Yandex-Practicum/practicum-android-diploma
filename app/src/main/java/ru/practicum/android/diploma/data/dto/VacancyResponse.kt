package ru.practicum.android.diploma.data.dto

import ru.practicum.android.diploma.data.dto.model.VacancyFullItemDto

data class VacancyResponse(
    val items: VacancyFullItemDto
) : Response()

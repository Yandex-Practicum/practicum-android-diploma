package ru.practicum.android.diploma.data.dto.response

import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.model.VacancyFullItemDto

data class VacancyResponse(
    val items: VacancyFullItemDto
) : Response()

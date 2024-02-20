package ru.practicum.android.diploma.data.dto.response

import ru.practicum.android.diploma.data.network.Response
import ru.practicum.android.diploma.data.room.VacancyDetailsDto

data class VacancyDetailsSearchResponse(
    val dto: VacancyDetailsDto
) : Response()

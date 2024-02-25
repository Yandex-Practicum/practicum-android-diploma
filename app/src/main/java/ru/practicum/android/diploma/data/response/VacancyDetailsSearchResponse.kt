package ru.practicum.android.diploma.data.response

import ru.practicum.android.diploma.data.search.network.Response
import ru.practicum.android.diploma.data.dto.favourites.room.VacancyDetailsDto

data class VacancyDetailsSearchResponse(
    val dto: VacancyDetailsDto
) : Response()

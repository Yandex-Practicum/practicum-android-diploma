package ru.practicum.android.diploma.data.response

import ru.practicum.android.diploma.data.dto.favourites.room.VacancyDetailsDto
import ru.practicum.android.diploma.data.search.network.Response

data class VacancyDetailsSearchResponse(
    val dto: VacancyDetailsDto
) : Response()

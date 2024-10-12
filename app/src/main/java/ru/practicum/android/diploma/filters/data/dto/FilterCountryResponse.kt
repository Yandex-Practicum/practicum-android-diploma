package ru.practicum.android.diploma.filters.data.dto

import ru.practicum.android.diploma.util.network.Response
import ru.practicum.android.diploma.vacancy.data.dto.AreaDto

class FilterCountryResponse(
    val areas: List<AreaDto>
) : Response()

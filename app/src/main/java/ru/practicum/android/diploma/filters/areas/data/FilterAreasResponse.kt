package ru.practicum.android.diploma.filters.areas.data

import ru.practicum.android.diploma.util.network.Response
import ru.practicum.android.diploma.vacancy.data.dto.AreaDto

class FilterAreasResponse(
    val areas: List<AreaDto>
) : Response()

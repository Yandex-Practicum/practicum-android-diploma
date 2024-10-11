package ru.practicum.android.diploma.filters.data.network

import ru.practicum.android.diploma.util.network.Response
import ru.practicum.android.diploma.vacancy.data.dto.AreaDto

class AreasResponse(
    val areas: List<AreaDto>
) : Response()

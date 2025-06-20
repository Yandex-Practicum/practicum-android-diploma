package ru.practicum.android.diploma.data.filters

import ru.practicum.android.diploma.data.Response
import ru.practicum.android.diploma.data.dto.IndustriesDto

data class IndustriesResponse(
    val data: List<IndustriesDto>
) : Response()

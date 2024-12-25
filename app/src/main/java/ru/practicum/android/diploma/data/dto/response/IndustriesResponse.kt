package ru.practicum.android.diploma.data.dto.response

import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.model.industries.IndustriesFullDto

data class IndustriesResponse(
    val industries: List<IndustriesFullDto>
) : Response()

package ru.practicum.android.diploma.data.dto.filter

import ru.practicum.android.diploma.data.dto.Response

data class IndustryResponse(
    val industries: List<IndustryDto>
) : Response()

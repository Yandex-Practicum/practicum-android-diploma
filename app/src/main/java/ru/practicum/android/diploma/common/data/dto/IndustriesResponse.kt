package ru.practicum.android.diploma.common.data.dto

import ru.practicum.android.diploma.filter.data.dto.model.IndustryDto

data class IndustriesResponse(
    val result: List<IndustryDto>
) : Response()

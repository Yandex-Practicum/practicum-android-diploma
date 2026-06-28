package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.dto.FilterIndustriesResponse
import ru.practicum.android.diploma.data.dto.FilterIndustryDto
import ru.practicum.android.diploma.domain.models.Industry
fun FilterIndustriesResponse.toModel(): List<Industry> {
    return results.map { dto ->
        Industry(
            industryId = dto.id.toString(),
            industryName = dto.name
        )
    }
}

package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.dto.FilterIndustryDto
import ru.practicum.android.diploma.domain.models.Industry

fun FilterIndustryDto.toModel(): Industry = Industry(
    industryId = this.id.toString(),
    industryName = this.name
)

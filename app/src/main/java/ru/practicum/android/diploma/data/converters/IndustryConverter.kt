package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.dto.IndustryDto
import ru.practicum.android.diploma.domain.Industry

fun IndustryDto.toModel(): Industry = Industry(
    industryId = this.id.toString(),
    industryName = this.name
)

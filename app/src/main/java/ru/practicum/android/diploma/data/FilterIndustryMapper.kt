package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.dto.FilterIndustryDto
import ru.practicum.android.diploma.domain.models.FilterIndustry

fun FilterIndustryDto.toDomain(): FilterIndustry = FilterIndustry(
    id = id,
    name = name,
)

fun List<FilterIndustryDto>.toDomain(): List<FilterIndustry> = map { industryDto ->
    industryDto.toDomain()
}

package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.dto.FilterAreaDto
import ru.practicum.android.diploma.data.dto.FilterIndustryDto
import ru.practicum.android.diploma.domain.models.FilterArea
import ru.practicum.android.diploma.domain.models.FilterIndustry

fun FilterAreaDto.toDomain(): FilterArea = FilterArea(
    id = id,
    name = name,
    parentId = parentId,
    areas = areas.map { it.toDomain() }
)

fun List<FilterAreaDto>.toDomain(): List<FilterArea> = map { areaDto ->
    areaDto.toDomain()
}


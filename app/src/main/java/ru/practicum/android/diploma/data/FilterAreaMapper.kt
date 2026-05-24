package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.dto.FilterAreaDto
import ru.practicum.android.diploma.domain.models.FilterArea

fun FilterAreaDto.toDomain(): FilterArea = FilterArea(
    id = id,
    name = name,
    parentId = parentId,
    areas = areas.map { it.toDomain() }
)

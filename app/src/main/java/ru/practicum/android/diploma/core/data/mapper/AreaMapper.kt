package ru.practicum.android.diploma.core.data.mapper

import ru.practicum.android.diploma.core.data.network.dto.AreasDto
import ru.practicum.android.diploma.filter.area.domain.model.Area

fun List<AreasDto>.mapToDomain(): List<Area> {
    return this.map { Area(it.id, it.name, it.parentId) }
}

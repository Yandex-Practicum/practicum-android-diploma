package ru.practicum.android.diploma.filters.areas.domain.impl

import ru.practicum.android.diploma.filters.areas.domain.models.Area
import ru.practicum.android.diploma.util.network.RegionInformationDto

class RegionToAreaConverter {
    fun map(dto: RegionInformationDto): Area {
        return Area(
            id = dto.areaId,
            name = dto.name,
            parentId = null,
            areas = mutableListOf()
        )
    }
}

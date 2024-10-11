package ru.practicum.android.diploma.filter.util

import ru.practicum.android.diploma.data.networkclient.api.dto.response.common.area.AreaInVacancy
import ru.practicum.android.diploma.data.networkclient.api.dto.response.regions.HHRegionsResponse
import ru.practicum.android.diploma.filter.domain.model.AreaInReference
import ru.practicum.android.diploma.data.networkclient.api.dto.response.common.area.AreaInReference as AreaInReferenceDto

internal class AreaConverter {
    fun map(areas: List<AreaInVacancy>): List<AreaInVacancy> = areas.map { map(it) }
    fun mapAreaReference(areas: List<AreaInReferenceDto>): List<AreaInReference> = areas.map { map(it) }

    fun map(area: AreaInVacancy): AreaInVacancy = with(area) { AreaInVacancy(id = id, name = name, url = url) }
    fun map(areaInReferernceDto: AreaInReferenceDto): AreaInReference = with(areaInReferernceDto) {
        AreaInReference(
            areas = mapAreaReference(areaInReferernceDto.areas),
            id = areaInReferernceDto.id,
            name = areaInReferernceDto.name,
            parentId = areaInReferernceDto.parentId
        )
    }

    fun map(regions: HHRegionsResponse): List<AreaInReference> = regions.map {
        AreaInReference(mapAreaReference(it.areas), it.id, it.name, it.parentId ?: "")
    }
}

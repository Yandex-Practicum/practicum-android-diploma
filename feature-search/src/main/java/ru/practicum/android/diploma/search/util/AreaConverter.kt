package ru.practicum.android.diploma.search.util

import ru.practicum.android.diploma.data.networkclient.api.dto.response.regions.HHRegionsResponse
import ru.practicum.android.diploma.search.domain.models.Area
import ru.practicum.android.diploma.search.domain.models.AreaX
import ru.practicum.android.diploma.search.domain.models.Region
import ru.practicum.android.diploma.search.domain.models.RegionList
import ru.practicum.android.diploma.data.networkclient.api.dto.response.common.area.Area as AreaDto
import ru.practicum.android.diploma.data.networkclient.api.dto.response.common.area.AreaX as AreaXDto

class AreaConverter {
    fun map(regions: HHRegionsResponse): RegionList = RegionList(ArrayList(regions.map {
        Region(areas = mapX(it.areas), id = it.id, name = it.name, parentId = it.parentId)
    }))

    fun map(areas: List<AreaDto>): List<Area> = areas.map { map(it) }
    fun mapX(areas: List<AreaXDto>): List<AreaX> = areas.map { map(it) }
    fun map(area: AreaDto): Area = with(area) { Area(id = id, name = name, url = url) }
    fun map(areaX: AreaXDto): AreaX =
        with(areaX) { AreaX(areas = map(areaX.areas), id = areaX.id, name = areaX.name, parentId = areaX.parentId) }
}

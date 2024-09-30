package ru.practicum.android.diploma.search.util

import ru.practicum.android.diploma.data.networkclient.api.dto.HHRegionsResponse
import ru.practicum.android.diploma.search.domain.models.Area
import ru.practicum.android.diploma.search.domain.models.AreaX
import ru.practicum.android.diploma.search.domain.models.Region
import ru.practicum.android.diploma.search.domain.models.RegionList
import ru.practicum.android.diploma.data.networkclient.api.dto.Area as AreaDto
import ru.practicum.android.diploma.data.networkclient.api.dto.AreaX as AreaXDto

class AreaConverter {
    fun map(regions: HHRegionsResponse): RegionList = RegionList(ArrayList(regions.map { it ->
        Region(map(it.areas), it.id, it.name, it.parentId)
    }))

    fun map(areas: List<AreaDto>): List<Area> = areas.map { map(it) }
    fun map(areas: List<AreaXDto>): List<AreaX> = areas.map { map(it) }
    fun map(area: AreaDto): Area = with(area) { Area(id, name, url) }
    fun map(areaX: AreaXDto): AreaX = with(areaX) { AreaX(map(areaX.areas), areaX.id, areaX.name, areaX.parentId) }
}

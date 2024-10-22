package ru.practicum.android.diploma.search.util

import ru.practicum.android.diploma.data.networkclient.api.dto.response.regions.HHRegionsResponse
import ru.practicum.android.diploma.search.domain.models.AreaInReference
import ru.practicum.android.diploma.search.domain.models.AreaInVacancy
import ru.practicum.android.diploma.search.domain.models.Region
import ru.practicum.android.diploma.search.domain.models.RegionList
import ru.practicum.android.diploma.data.networkclient.api.dto.response.common.area.AreaInReference as AreaInReferenceDto
import ru.practicum.android.diploma.data.networkclient.api.dto.response.common.area.AreaInVacancy as AreaInVacancyDto

internal class AreaConverter {
    fun map(regions: HHRegionsResponse): RegionList = RegionList(ArrayList(regions.map {
        Region(areas = mapAreaReference(it.areas), id = it.id, name = it.name, parentId = it.parentId)
    }))

    fun map(areas: List<AreaInVacancyDto>): List<AreaInVacancy> = areas.map { map(it) }
    private fun mapAreaReference(areas: List<AreaInReferenceDto>): List<AreaInReference> = areas.map { map(it) }
    private fun map(
        area: AreaInVacancyDto
    ): AreaInVacancy = with(area) { AreaInVacancy(id = id, name = name, url = url) }
    private fun map(areaInReferenceDto: AreaInReferenceDto): AreaInReference =
        AreaInReference(
            areas = mapAreaReference(areaInReferenceDto.areas),
            id = areaInReferenceDto.id,
            name = areaInReferenceDto.name,
            parentId = areaInReferenceDto.parentId
        )
}

package ru.practicum.android.diploma.filter.place.data.mappers

import ru.practicum.android.diploma.data.networkclient.api.dto.response.regions.HHRegionsResponse
import ru.practicum.android.diploma.filter.place.domain.model.AreaInReference
import ru.practicum.android.diploma.filter.place.domain.model.Region

internal object AreaMapper {

    fun map(regions: HHRegionsResponse): List<AreaInReference> {
        return regions.map { region ->
            AreaInReference(
                areas = region.areas.map { areaInReferenceDto ->
                    Region(
                        id = areaInReferenceDto.id,
                        name = areaInReferenceDto.name,
                        parentId = areaInReferenceDto.parentId,
                        parentName = region.name
                    )
                },
                id = region.id,
                name = region.name
            )
        }
    }
}

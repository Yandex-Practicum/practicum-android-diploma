package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.dto.response.RegionResponse
import ru.practicum.android.diploma.domain.models.Region

class RegionsConverter {

    fun convertRegions(response: RegionResponse): List<Region> {
        return response.areas.map {
            convertRegion(it)
        }
    }

    fun convertRegion(region: RegionResponse): Region {
        return Region(
            includedRegions = region.areas.map { convertRegion(it) },
            id = region.id?.let { region.id },
            name = region.name?.let { region.name },
            parentId = region.parentId?.let { region.parentId }
        )
    }
}

package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.dto.response.RegionResponse
import ru.practicum.android.diploma.domain.models.Country
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
            id = region.id ?: "",
            name = region.name ?: "",
            parentId = region.parentId
        )
    }

    fun convertRegionToCountry(region: RegionResponse): Country {
        return Country(
            id = region.id ?: "",
            name = region.name ?: "",
            parentId = region.parentId,
            includedRegions = ArrayList()
        )
    }
}

package ru.practicum.android.diploma.filter.place.data.mappers

import ru.practicum.android.diploma.cache.dto.CountryCache
import ru.practicum.android.diploma.cache.dto.RegionCache
import ru.practicum.android.diploma.filter.place.domain.model.AreaInReference
import ru.practicum.android.diploma.filter.place.domain.model.Region

object CacheMapper {
    fun map(countryCache: CountryCache): AreaInReference {
        return with(countryCache) {
            AreaInReference(
                areas = regions.map { regions ->
                    Region(
                        id = regions.idRegion,
                        name = regions.nameRegion,
                        parentId = regions.idCountry,
                        parentName = nameCountry
                    )
                },
                id = idCountry,
                name = nameCountry
            )
        }
    }

    fun map(areaInReference: AreaInReference): CountryCache {
        return with(areaInReference) {
            CountryCache(
                idCountry = id,
                nameCountry = name,
                regions = areas.map { regions ->
                    RegionCache(
                        idCountry = regions.parentId,
                        idRegion = regions.id,
                        nameRegion = regions.name
                    )
                }
            )
        }
    }
}

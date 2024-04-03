package ru.practicum.android.diploma.domain.region.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.country.Country
import ru.practicum.android.diploma.domain.region.RegionInteractor
import ru.practicum.android.diploma.domain.region.RegionRepository
import ru.practicum.android.diploma.util.ResourceContentSearch

class RegionInteractorImpl(
    val regionRepository: RegionRepository
) : RegionInteractor {

    override fun searchRegion(regionId: String): Flow<Pair<Country?, Int?>> {
        return regionRepository.searchRegion(regionId).map { resource ->
            when (resource) {
                is ResourceContentSearch.SuccessSearch -> Pair(resource.data, null)
                is ResourceContentSearch.ErrorSearch -> Pair(null, resource.message)
                is ResourceContentSearch.ServerErrorSearch -> Pair(null, 500)
            }
        }
    }
}

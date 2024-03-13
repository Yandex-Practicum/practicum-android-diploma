package ru.practicum.android.diploma.domain.region.impl

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.country.Country
import ru.practicum.android.diploma.domain.region.RegionInteractor
import ru.practicum.android.diploma.domain.region.RegionRepository
import ru.practicum.android.diploma.util.Resource

class RegionInteractorImpl(
    val regionRepository: RegionRepository
) : RegionInteractor {

    override fun searchRegion(regionId: String): Flow<Pair<Country?, Int?>> {
        Log.d("RegionState", "Прокидываем в Interactor ID = $regionId")
        return regionRepository.searchRegion(regionId).map { resource ->
            when (resource) {
                is Resource.Success -> Pair(resource.data, null)
                is Resource.Error -> Pair(null, resource.message)
                is Resource.ServerError -> Pair(null, 500)
            }
        }
    }
}

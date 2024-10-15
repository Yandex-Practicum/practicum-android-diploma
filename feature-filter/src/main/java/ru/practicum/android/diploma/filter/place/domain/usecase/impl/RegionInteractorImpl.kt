package ru.practicum.android.diploma.filter.place.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.commonutils.Resource
import ru.practicum.android.diploma.filter.place.domain.model.AreaInReference
import ru.practicum.android.diploma.filter.place.domain.model.Place
import ru.practicum.android.diploma.filter.place.domain.repository.RegionNetworkRepository
import ru.practicum.android.diploma.filter.place.domain.repository.RegionSpRepository
import ru.practicum.android.diploma.filter.place.domain.usecase.RegionInteractor

internal class RegionInteractorImpl(
    private val regionNetworkRepository: RegionNetworkRepository,
    private val regionSpRepository: RegionSpRepository,
) : RegionInteractor {

    override fun listAreas(): Flow<Pair<List<AreaInReference>?, String?>> {
        return regionNetworkRepository.listAreas().map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, "")
                }

                is Resource.Error -> {
                    Pair(null, result.message ?: "")
                }
            }
        }
    }

    override suspend fun getPlaceDataFilter(): Place? {
        return regionSpRepository.getPlaceDataFilter()
    }

    override suspend fun updatePlaceInDataFilter(place: Place): Int {
        return regionSpRepository.updatePlaceInDataFilter(place)
    }
}

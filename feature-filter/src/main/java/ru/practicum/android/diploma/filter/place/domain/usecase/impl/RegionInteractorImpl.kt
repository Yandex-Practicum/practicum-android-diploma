package ru.practicum.android.diploma.filter.place.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.commonutils.Resource
import ru.practicum.android.diploma.filter.place.domain.model.AreaInReference
import ru.practicum.android.diploma.filter.place.domain.model.Place
import ru.practicum.android.diploma.filter.place.domain.repository.CacheRepository
import ru.practicum.android.diploma.filter.place.domain.repository.NetworkRepository
import ru.practicum.android.diploma.filter.place.domain.repository.SpRepository
import ru.practicum.android.diploma.filter.place.domain.usecase.RegionInteractor

internal class RegionInteractorImpl(
    private val networkRepository: NetworkRepository,
    private val spRepository: SpRepository,
    private val cacheRepository: CacheRepository,
) : RegionInteractor {

    override fun listAreas(): Flow<Pair<List<AreaInReference>?, String?>> {
        return networkRepository.listAreas().map { result ->
            Resource.handleResource(result)
        }
    }

    override suspend fun getPlaceDataFilterBuffer(): Place? {
        return spRepository.getPlaceDataFilterBuffer()
    }

    override suspend fun updatePlaceInDataFilterBuffer(place: Place): Int {
        return spRepository.updatePlaceInDataFilterBuffer(place)
    }

    override suspend fun clearPlaceInDataFilterBuffer(): Int {
        return spRepository.clearPlaceInDataFilterBuffer()
    }

    override suspend fun getPlaceDataFilterReserveBuffer(): Place? {
        return spRepository.getPlaceDataFilterReserveBuffer()
    }

    override suspend fun updatePlaceInDataFilterReserveBuffer(place: Place): Int {
        return spRepository.updatePlaceInDataFilterReserveBuffer(place)
    }

    override suspend fun clearPlaceInDataFilterReserveBuffer(): Int {
        return spRepository.clearPlaceInDataFilterReserveBuffer()
    }

    override suspend fun putCountriesCache(countries: List<AreaInReference>) {
        cacheRepository.putCountries(countries)
    }

    override suspend fun getCountriesCache(): List<AreaInReference>? {
        return cacheRepository.getCountries()
    }

    override suspend fun clearCache() {
        cacheRepository.clearCache()
    }
}

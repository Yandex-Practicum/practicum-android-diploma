package ru.practicum.android.diploma.filter.place.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.place.domain.model.AreaInReference
import ru.practicum.android.diploma.filter.place.domain.model.Place

interface RegionInteractor {

    fun listAreas(): Flow<Pair<List<AreaInReference>?, String?>>

    suspend fun getPlaceDataFilter(): Place?
    suspend fun updatePlaceInDataFilter(place: Place): Int
    suspend fun clearPlaceInDataFilter(): Int
    suspend fun getPlaceDataFilterBuffer(): Place?
    suspend fun updatePlaceInDataFilterBuffer(place: Place): Int
    suspend fun clearPlaceInDataFilterBuffer(): Int

    suspend fun putCountriesCache(countries: List<AreaInReference>)
    suspend fun getCountriesCache(): List<AreaInReference>?
    suspend fun clearCache()
}

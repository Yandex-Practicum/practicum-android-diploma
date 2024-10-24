package ru.practicum.android.diploma.filter.place.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.sp.api.SpResult
import ru.practicum.android.diploma.filter.place.domain.model.AreaInReference
import ru.practicum.android.diploma.filter.place.domain.model.Place

internal interface RegionInteractor {

    fun listAreas(): Flow<Pair<List<AreaInReference>?, String?>>

    suspend fun getPlaceDataFilterBuffer(): Place?
    suspend fun updatePlaceInDataFilterBuffer(place: Place): SpResult
    suspend fun clearPlaceInDataFilterBuffer(): SpResult
    suspend fun getPlaceDataFilterReserveBuffer(): Place?
    suspend fun updatePlaceInDataFilterReserveBuffer(place: Place): SpResult
    suspend fun clearPlaceInDataFilterReserveBuffer(): SpResult

    suspend fun putCountriesCache(countries: List<AreaInReference>)
    suspend fun getCountriesCache(): List<AreaInReference>?
    suspend fun clearCache()
}

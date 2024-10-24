package ru.practicum.android.diploma.filter.place.domain.repository

import ru.practicum.android.diploma.data.sp.api.SpResult
import ru.practicum.android.diploma.filter.place.domain.model.Place

internal interface SpRepository {
    suspend fun getPlaceDataFilterBuffer(): Place?
    suspend fun updatePlaceInDataFilterBuffer(place: Place): SpResult
    suspend fun clearPlaceInDataFilterBuffer(): SpResult
    suspend fun getPlaceDataFilterReserveBuffer(): Place?
    suspend fun updatePlaceInDataFilterReserveBuffer(place: Place): SpResult
    suspend fun clearPlaceInDataFilterReserveBuffer(): SpResult
}

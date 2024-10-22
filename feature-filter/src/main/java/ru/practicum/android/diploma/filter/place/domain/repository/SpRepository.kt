package ru.practicum.android.diploma.filter.place.domain.repository

import ru.practicum.android.diploma.filter.place.domain.model.Place

internal interface SpRepository {
    suspend fun getPlaceDataFilterBuffer(): Place?
    suspend fun updatePlaceInDataFilterBuffer(place: Place): Int
    suspend fun clearPlaceInDataFilterBuffer(): Int
    suspend fun getPlaceDataFilterReserveBuffer(): Place?
    suspend fun updatePlaceInDataFilterReserveBuffer(place: Place): Int
    suspend fun clearPlaceInDataFilterReserveBuffer(): Int
}

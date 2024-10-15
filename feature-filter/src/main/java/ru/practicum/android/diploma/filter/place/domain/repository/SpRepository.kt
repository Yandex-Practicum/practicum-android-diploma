package ru.practicum.android.diploma.filter.place.domain.repository

import ru.practicum.android.diploma.filter.place.domain.model.Place

internal interface SpRepository {
    suspend fun getPlaceDataFilter(): Place?
    suspend fun updatePlaceInDataFilter(placeDto: Place): Int
    suspend fun clearPlaceInDataFilter(): Int
    suspend fun getPlaceDataFilterBuffer(): Place?
    suspend fun updatePlaceInDataFilterBuffer(placeDto: Place): Int
    suspend fun clearPlaceInDataFilterBuffer(): Int
}

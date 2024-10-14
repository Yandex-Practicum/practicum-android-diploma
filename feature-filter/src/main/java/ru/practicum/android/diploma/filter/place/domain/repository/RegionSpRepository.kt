package ru.practicum.android.diploma.filter.place.domain.repository

import ru.practicum.android.diploma.filter.place.domain.model.Place

internal interface RegionSpRepository {
    suspend fun getPlaceDataFilter() : Place?
    suspend fun updatePlaceInDataFilter(placeDto: Place) : Int
}

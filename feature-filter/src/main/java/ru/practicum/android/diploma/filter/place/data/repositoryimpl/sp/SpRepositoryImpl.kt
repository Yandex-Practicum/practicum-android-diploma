package ru.practicum.android.diploma.filter.place.data.repositoryimpl.sp

import ru.practicum.android.diploma.data.sp.api.FilterSp
import ru.practicum.android.diploma.filter.place.data.mappers.SpMapper
import ru.practicum.android.diploma.filter.place.domain.model.Place
import ru.practicum.android.diploma.filter.place.domain.repository.SpRepository

internal class SpRepositoryImpl(
    private val filterSp: FilterSp
) : SpRepository {

    override suspend fun getPlaceDataFilterBuffer(): Place? {
        return filterSp.getPlaceDataFilterBuffer()?.let { SpMapper.map(it) }
    }

    override suspend fun updatePlaceInDataFilterBuffer(place: Place): Int {
        return filterSp.updatePlaceInDataFilterBuffer(SpMapper.map(place))
    }

    override suspend fun clearPlaceInDataFilterBuffer(): Int {
        return filterSp.updatePlaceInDataFilterBuffer(SpMapper.mapClear())
    }

    override suspend fun getPlaceDataFilterReserveBuffer(): Place? {
        return filterSp.getPlaceDataFilterReserveBuffer()?.let { SpMapper.map(it) }
    }

    override suspend fun updatePlaceInDataFilterReserveBuffer(place: Place): Int {
        return filterSp.updatePlaceInDataFilterReserveBuffer(SpMapper.map(place))
    }

    override suspend fun clearPlaceInDataFilterReserveBuffer(): Int {
        return filterSp.updatePlaceInDataFilterReserveBuffer(SpMapper.mapClear())
    }
}

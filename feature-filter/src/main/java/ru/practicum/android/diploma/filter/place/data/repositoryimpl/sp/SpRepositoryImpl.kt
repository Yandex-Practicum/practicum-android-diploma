package ru.practicum.android.diploma.filter.place.data.repositoryimpl.sp

import ru.practicum.android.diploma.data.sp.api.FilterSp
import ru.practicum.android.diploma.data.sp.api.SpResult
import ru.practicum.android.diploma.filter.place.data.mappers.SpMapper
import ru.practicum.android.diploma.filter.place.domain.model.Place
import ru.practicum.android.diploma.filter.place.domain.repository.SpRepository

internal class SpRepositoryImpl(
    private val filterSp: FilterSp
) : SpRepository {

    override suspend fun getPlaceDataFilterBuffer(): Place? {
        return filterSp.getPlaceDataFilterBuffer()?.let { SpMapper.map(it) }
    }

    override suspend fun updatePlaceInDataFilterBuffer(place: Place): SpResult {
        return filterSp.updatePlaceInDataFilterBuffer(SpMapper.map(place))
    }

    override suspend fun clearPlaceInDataFilterBuffer(): SpResult {
        return filterSp.updatePlaceInDataFilterBuffer(SpMapper.mapClear())
    }

    override suspend fun getPlaceDataFilterReserveBuffer(): Place? {
        return filterSp.getPlaceDataFilterReserveBuffer()?.let { SpMapper.map(it) }
    }

    override suspend fun updatePlaceInDataFilterReserveBuffer(place: Place): SpResult {
        return filterSp.updatePlaceInDataFilterReserveBuffer(SpMapper.map(place))
    }

    override suspend fun clearPlaceInDataFilterReserveBuffer(): SpResult {
        return filterSp.updatePlaceInDataFilterReserveBuffer(SpMapper.mapClear())
    }
}

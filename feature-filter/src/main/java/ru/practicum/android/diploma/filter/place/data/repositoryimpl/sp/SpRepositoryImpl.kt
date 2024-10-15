package ru.practicum.android.diploma.filter.place.data.repositoryimpl.sp

import ru.practicum.android.diploma.data.sp.api.FilterSp
import ru.practicum.android.diploma.filter.place.data.mappers.SpMapper
import ru.practicum.android.diploma.filter.place.domain.model.Place
import ru.practicum.android.diploma.filter.place.domain.repository.SpRepository

class SpRepositoryImpl(
    val filterSp: FilterSp
) : SpRepository {

    override suspend fun getPlaceDataFilter(): Place? {
        return filterSp.getPlaceDataFilter()?.let { SpMapper.map(it) }
    }

    override suspend fun updatePlaceInDataFilter(place: Place): Int {
        return filterSp.updatePlaceInDataFilter(SpMapper.map(place))
    }

    override suspend fun clearPlaceInDataFilter(): Int {
        return filterSp.updatePlaceInDataFilter(SpMapper.mapClear())
    }

    override suspend fun getPlaceDataFilterBuffer(): Place? {
        return filterSp.getPlaceDataFilterBuffer()?.let { SpMapper.map(it) }
    }

    override suspend fun updatePlaceInDataFilterBuffer(place: Place): Int {
        return filterSp.updatePlaceInDataFilterBuffer(SpMapper.map(place))
    }

    override suspend fun clearPlaceInDataFilterBuffer(): Int {
        return filterSp.updatePlaceInDataFilterBuffer(SpMapper.mapClear())
    }
}

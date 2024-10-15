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
}

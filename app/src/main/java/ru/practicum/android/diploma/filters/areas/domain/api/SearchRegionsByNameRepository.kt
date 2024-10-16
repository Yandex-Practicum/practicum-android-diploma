package ru.practicum.android.diploma.filters.areas.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.network.RegionInformation

interface SearchRegionsByNameRepository {
    fun setText(text: String)
    fun setParentId(parentId: String)
    fun searchRegionsByName(): Flow<Resource<List<RegionInformation>>>
}

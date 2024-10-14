package ru.practicum.android.diploma.filters.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filters.domain.models.Area
import ru.practicum.android.diploma.util.Resource

interface FilterAreaRepository {
    fun getAreas(): Flow<Resource<List<Area>>>
    fun loadAllRegions(): Flow<Resource<List<Area>>>
    fun getAllRegions(): Flow<Resource<List<Area>>>
}

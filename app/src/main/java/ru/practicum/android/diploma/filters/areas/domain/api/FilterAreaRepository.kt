package ru.practicum.android.diploma.filters.areas.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filters.areas.domain.models.Area
import ru.practicum.android.diploma.util.Resource

interface FilterAreaRepository {
    fun getAreas(): Flow<Resource<List<Area>>>
}

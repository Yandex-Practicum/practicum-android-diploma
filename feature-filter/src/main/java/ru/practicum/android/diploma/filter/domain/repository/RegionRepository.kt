package ru.practicum.android.diploma.filter.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.commonutils.Resource
import ru.practicum.android.diploma.filter.domain.model.AreaInReference

internal interface RegionRepository {
    fun listAreas(): Flow<Resource<List<AreaInReference>>>
}

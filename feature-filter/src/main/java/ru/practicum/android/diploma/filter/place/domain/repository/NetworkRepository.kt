package ru.practicum.android.diploma.filter.place.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.commonutils.Resource
import ru.practicum.android.diploma.filter.place.domain.model.AreaInReference

internal interface NetworkRepository {
    fun listAreas(): Flow<Resource<List<AreaInReference>>>
}

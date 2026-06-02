package ru.practicum.android.diploma.core.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.Resource
import ru.practicum.android.diploma.core.domain.models.Area

interface AreaRepository {
    fun getCountries(): Flow<Resource<List<Area>>>
}

package ru.practicum.android.diploma.domain.filters

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Areas
import ru.practicum.android.diploma.util.Resource

interface AreasRepository {
    fun getAreas(): Flow<Resource<List<Areas>>>
}

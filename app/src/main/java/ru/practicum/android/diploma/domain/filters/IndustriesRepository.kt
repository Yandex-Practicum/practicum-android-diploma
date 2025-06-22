package ru.practicum.android.diploma.domain.filters

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Industries
import ru.practicum.android.diploma.util.Resource

interface IndustriesRepository {
    fun getIndustries(): Flow<Resource<List<Industries>>>
}

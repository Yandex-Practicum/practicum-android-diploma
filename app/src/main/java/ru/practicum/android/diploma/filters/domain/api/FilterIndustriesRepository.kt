package ru.practicum.android.diploma.filters.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filters.domain.models.Industry
import ru.practicum.android.diploma.util.Resource

interface FilterIndustriesRepository {
    fun getIndustries(): Flow<Resource<List<Industry>>>
}

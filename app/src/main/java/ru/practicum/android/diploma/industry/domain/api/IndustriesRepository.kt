package ru.practicum.android.diploma.industry.domain.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.core.domain.Resource
import ru.practicum.android.diploma.core.domain.models.Industries

interface IndustriesRepository {
    suspend fun getIndustries(query: String): Flow<Resource<Industries>>
}

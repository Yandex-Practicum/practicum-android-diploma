package ru.practicum.android.diploma.industry.domain.api

import ru.practicum.android.diploma.core.data.network.Resource
import ru.practicum.android.diploma.industry.domain.models.Industries

interface IndustriesRepository {
    suspend fun getIndustries(query: String): Resource<Industries>
}

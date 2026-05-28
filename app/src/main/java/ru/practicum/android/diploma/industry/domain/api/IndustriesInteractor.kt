package ru.practicum.android.diploma.industry.domain.api

import ru.practicum.android.diploma.core.data.network.Resource
import ru.practicum.android.diploma.core.domain.models.Industry

interface IndustriesInteractor {
    suspend fun getIndustries(query: String): Resource<List<Industry>>

    fun applyFilter(industry: Industry?)
}

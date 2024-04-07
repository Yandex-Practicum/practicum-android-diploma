package ru.practicum.android.diploma.domain.industries

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.industries.ChildIndustry
import ru.practicum.android.diploma.util.ResourceContentSearch

interface IndustriesRepository {
    fun getIndustries(): Flow<ResourceContentSearch<List<ChildIndustry>>>
}

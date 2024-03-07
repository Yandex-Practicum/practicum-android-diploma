package ru.practicum.android.diploma.domain.industries

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.util.Resource

interface IndustriesRepository {
    fun searchIndustries(): Flow<Resource<List<ParentIndustriesAllDeal>>>
}

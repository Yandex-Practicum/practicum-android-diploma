package ru.practicum.android.diploma.domain.industries

import kotlinx.coroutines.flow.Flow

interface IndustriesInteractor {
    fun searchIndustries(): Flow<Pair<List<ParentIndustriesAllDeal>?, Int?>>
}

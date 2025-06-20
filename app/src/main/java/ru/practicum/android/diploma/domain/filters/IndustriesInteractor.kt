package ru.practicum.android.diploma.domain.filters

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Industries

interface IndustriesInteractor {
    fun getIndustries(): Flow<Pair<List<Industries>?, Int?>>
}

package ru.practicum.android.diploma.domain.industries

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.response.Industries

interface IndustriesInteractor {
    fun searchIndustries(): Flow<Pair<List<Industries>?, Int?>>
}

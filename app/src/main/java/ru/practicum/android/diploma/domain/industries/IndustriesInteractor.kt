package ru.practicum.android.diploma.domain.industries

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.industries.ChildIndustry

interface IndustriesInteractor {
    fun getIndustries(): Flow<Pair<List<ChildIndustry>?, Int?>>
}

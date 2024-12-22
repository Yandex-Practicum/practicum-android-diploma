package ru.practicum.android.diploma.domain.industries

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.dto.IndustriesResponse

interface IndustriesInteractor {
    fun getIndustries() : Flow<Pair<IndustriesResponse?, String?>>
}

package ru.practicum.android.diploma.data.dto.industries

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.dto.IndustriesResponse
import ru.practicum.android.diploma.util.Resource


interface IndustriesRepository {
    fun getIndustries(): Flow<Resource<IndustriesResponse>>
}

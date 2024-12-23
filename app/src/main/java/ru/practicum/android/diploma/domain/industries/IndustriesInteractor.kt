package ru.practicum.android.diploma.domain.industries

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.dto.model.industries.IndustriesFullDto

interface IndustriesInteractor {
    fun getIndustries(): Flow<List<IndustriesFullDto>>
}

package ru.practicum.android.diploma.filter.industry.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.industry.domain.model.Industry
import ru.practicum.android.diploma.filter.industry.domain.model.IndustryError
import ru.practicum.android.diploma.util.Result

interface IndustryRepository {
    fun getIndustries(): Flow<Result<List<Industry>, IndustryError>>
}

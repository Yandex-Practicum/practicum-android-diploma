package ru.practicum.android.diploma.filter.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.model.IndustryViewState

interface FilterRepository {
    fun getIndustries(): Flow<IndustryViewState>
}

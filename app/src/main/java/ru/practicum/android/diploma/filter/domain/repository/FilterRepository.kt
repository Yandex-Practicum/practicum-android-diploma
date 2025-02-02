package ru.practicum.android.diploma.filter.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.model.Country
import ru.practicum.android.diploma.filter.domain.model.IndustryViewState
import ru.practicum.android.diploma.filter.domain.model.Region

interface FilterRepository {
    fun getIndustries(): Flow<IndustryViewState>
    fun getCountry(): Country?
    fun getRegion(): Region?
}

package ru.practicum.android.diploma.filter.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.model.CountryViewState
import ru.practicum.android.diploma.filter.domain.model.IndustryViewState
import ru.practicum.android.diploma.filter.domain.model.RegionViewState

interface FilterRepository {
    fun getIndustries(): Flow<IndustryViewState>
    fun getCountries(): Flow<CountryViewState>
    fun searchRegionsById(parentId: String): Flow<RegionViewState>
    fun getParentRegionById(parentId: String): Flow<CountryViewState>
    fun getAllRegions(): Flow<RegionViewState>
//    fun getAllNonCisRegions(): Flow<RegionViewState>
}

package ru.practicum.android.diploma.filter.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.model.CountryViewState
import ru.practicum.android.diploma.filter.domain.model.IndustryViewState
import ru.practicum.android.diploma.filter.domain.model.RegionViewState

interface FilterInteractor {
    fun getIndustries(): Flow<IndustryViewState>
    fun searchRegionsById(parentId: String): Flow<RegionViewState>
    fun getAllRegions(): Flow<RegionViewState>

//    fun getAllNonCisRegions(): Flow<RegionViewState>
    fun getCountries(): Flow<CountryViewState>
}

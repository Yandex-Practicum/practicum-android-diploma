package ru.practicum.android.diploma.domain.filter

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.filter.Area
import ru.practicum.android.diploma.domain.models.filter.Country
import ru.practicum.android.diploma.domain.models.filter.Filters
import ru.practicum.android.diploma.domain.models.filter.Industry
import ru.practicum.android.diploma.util.DataResponse

interface FilterInteractor {
    fun setSalary(input: String)

    fun getSalary(): String
    fun getAreas(areaId: String): Flow<DataResponse<Area>>

    fun getCountries(): Flow<Pair<List<Country>?, String?>>
    fun setSelectedCountry(country: Country?)
    fun getSelectedCountry(): Country?

    fun setSelectedArea(area: Area?)
    fun getSelectedArea(): Area?
    fun getFilters(): Filters
    fun clearFilters()

    fun getIndustries(): Flow<DataResponse<Industry>>

    fun setSelectedIdustries(industry: Industry?)

    fun getSelectedIndustries(): Industry?
}
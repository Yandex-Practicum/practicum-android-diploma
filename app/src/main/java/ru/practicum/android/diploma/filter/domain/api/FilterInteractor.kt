package ru.practicum.android.diploma.filter.domain.api

import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.filter.domain.models.Region
import ru.practicum.android.diploma.filter.domain.models.SelectedFilter


interface FilterInteractor {

    suspend fun getSavedFilterSettings(key: String): SelectedFilter
    suspend fun saveRegion(key: String, region: Region)
    suspend fun refreshCountry(key: String, country: Country)
    suspend fun saveIndustry(key: String, industry: Industry)
    suspend fun refreshSalary(key: String, salary: String)
}
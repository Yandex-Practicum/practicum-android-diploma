package ru.practicum.android.diploma.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.model.Industry
import ru.practicum.android.diploma.search.domain.model.Resource

interface FiltersRepository {
    fun getIndustries(): Flow<Resource<List<Industry>>>
    fun saveFilters(industry: Industry?, salary: String?, onlyWithSalary: Boolean)
    fun getSavedFilters(): Triple<Industry?, String?, Boolean>
    fun clearSavedFilters()
}

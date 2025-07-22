package ru.practicum.android.diploma.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.model.FailureType
import ru.practicum.android.diploma.search.domain.model.Industry

interface FiltersInteractor {
    fun getIndustries(): Flow<Pair<List<Industry>?, FailureType?>>
    fun saveFilters(industry: Industry?, salary: String?, onlyWithSalary: Boolean)
    fun getSavedFilters(): Triple<Industry?, String?, Boolean>
    fun clearSavedFilters()
}

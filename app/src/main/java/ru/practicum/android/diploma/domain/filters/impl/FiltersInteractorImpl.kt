package ru.practicum.android.diploma.domain.filters.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.filters.repository.FiltersInteractor
import ru.practicum.android.diploma.domain.filters.repository.FiltersRepository
import ru.practicum.android.diploma.domain.models.filters.Region
import ru.practicum.android.diploma.domain.models.filters.Country
import ru.practicum.android.diploma.domain.models.filters.Industry
import ru.practicum.android.diploma.util.Resource

class FiltersInteractorImpl(private val repository: FiltersRepository) : FiltersInteractor {
    override fun getCountries(): Flow<Resource<List<Country>>> = repository.getCountries()
    override fun getIndustries(): Flow<Resource<List<Industry>>> = repository.getIndustries()
    override fun getRegions(countryId: String): Flow<Resource<List<Region>>> =
        repository.getRegions(countryId)
}

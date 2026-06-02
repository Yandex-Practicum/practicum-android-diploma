package ru.practicum.android.diploma.country.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.core.domain.Resource
import ru.practicum.android.diploma.core.domain.models.Area
import ru.practicum.android.diploma.core.domain.repository.AreaRepository
import ru.practicum.android.diploma.core.domain.repository.FiltersRepository
import ru.practicum.android.diploma.country.domain.api.CountryInteractor

class CountryInteractorImpl(
    val repository: AreaRepository,
    val filtersRepository: FiltersRepository
) : CountryInteractor {
    override fun getCountries(): Flow<Resource<List<Area>>> = flow {
        repository.getCountries().collect {
            emit(it)
        }
    }

    override fun selectCountry(country: Area) {
        filtersRepository.applyCountry(country)
    }
}

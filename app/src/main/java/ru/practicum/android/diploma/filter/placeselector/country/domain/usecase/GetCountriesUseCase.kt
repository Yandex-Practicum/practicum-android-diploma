package ru.practicum.android.diploma.filter.placeselector.country.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.model.Country
import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import ru.practicum.android.diploma.util.Resource

class GetCountriesUseCase(private val filterRepository: FilterRepository) {
    fun execute(): Flow<Resource<List<Country>>> {
        return filterRepository.getCountries()
    }
}

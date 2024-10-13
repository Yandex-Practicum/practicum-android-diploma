package ru.practicum.android.diploma.filters.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.filters.domain.api.FilterCountryInteractor
import ru.practicum.android.diploma.filters.domain.api.FilterCountryRepository
import ru.practicum.android.diploma.filters.domain.models.Country
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.network.HttpStatusCode

class FilterCountryInteractorImpl(
    private val repository: FilterCountryRepository
) : FilterCountryInteractor {

    override fun getCountries(): Flow<Pair<List<Country>?, HttpStatusCode?>> {
        return repository.getAreas().map { result ->
            when (result) {
                is Resource.Success -> Pair(result.data, HttpStatusCode.OK)
                is Resource.Error -> Pair(null, result.httpStatusCode)
            }
        }
    }
}

package ru.practicum.android.diploma.domain.country.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.country.Country
import ru.practicum.android.diploma.domain.country.CountryInteractor
import ru.practicum.android.diploma.domain.country.CountryRepository
import ru.practicum.android.diploma.util.Resource

class CountryInteractorImpl(
    val countryRepository: CountryRepository
) : CountryInteractor {

    override fun searchIndustries(): Flow<Pair<List<Country>?, Int?>> {
        return countryRepository.searchRegion().map { resource ->
            when (resource) {
                is Resource.Success -> Pair(resource.data, null)
                is Resource.Error -> Pair(null, resource.message)
            }
        }
    }

}

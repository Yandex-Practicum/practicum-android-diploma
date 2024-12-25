package ru.practicum.android.diploma.domain.impl.country

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.api.country.CountriesInteractor
import ru.practicum.android.diploma.domain.api.country.CountriesRepository
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.ui.search.state.VacancyError
import ru.practicum.android.diploma.util.Resource

class CountriesInteractorImpl(private val countriesRepository: CountriesRepository) : CountriesInteractor {
    override fun getCountries(): Flow<Pair<List<Country>?, VacancyError?>> {
        return countriesRepository.getCountries().map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }
}

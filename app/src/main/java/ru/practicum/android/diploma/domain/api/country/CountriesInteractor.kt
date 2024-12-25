package ru.practicum.android.diploma.domain.api.country

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.ui.search.state.VacancyError

interface CountriesInteractor {

    fun getCountries(): Flow<Pair<List<Country>?, VacancyError?>>
}

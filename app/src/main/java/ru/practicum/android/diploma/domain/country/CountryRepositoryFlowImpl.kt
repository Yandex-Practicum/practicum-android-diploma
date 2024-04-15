package ru.practicum.android.diploma.domain.country

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.converter.AreaConverter.mapToCountry
import ru.practicum.android.diploma.data.network.SearchVacanciesApi
import ru.practicum.android.diploma.domain.debugLog

class CountryRepositoryFlowImpl(
    private val searchVacanciesApi: SearchVacanciesApi
): CountryRepositoryFlow {

    private val countryFlow: MutableStateFlow<List<Country>> = MutableStateFlow(emptyList())

    init {
        setCountryFlow()
    }

    private fun setCountryFlow() = flow<List<Country>> {
        try {
            val areas = searchVacanciesApi.getAllAreas()
            val countries = areas.map { it.mapToCountry() }
            countryFlow.value = countries
            debugLog(TAG) { "setCountryFlow: countries = ${countries.map { it.name }}" }
        } catch (e: Exception) {
            // В этом случае просто оставляем список стран пустым
            debugLog(TAG) { "Пусто" }
        }
    }

    override fun getCountryFlow(): StateFlow<List<Country>> = countryFlow

    companion object {
        const val TAG = "CountryRepositoryFlowImpl"
    }
}

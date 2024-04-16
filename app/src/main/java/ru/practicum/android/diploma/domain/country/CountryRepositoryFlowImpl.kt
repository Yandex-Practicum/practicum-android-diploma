package ru.practicum.android.diploma.domain.country

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.converter.AreaConverter.mapToCountry
import ru.practicum.android.diploma.data.network.SearchVacanciesApi
import ru.practicum.android.diploma.domain.debugLog

class CountryRepositoryFlowImpl(
    private val searchVacanciesApi: SearchVacanciesApi
) : CountryRepositoryFlow {

    private val countryNameByIdMap = mapOf(
        97 to "Узбекистан",
        9 to "Азербайджан",
        5 to "Украина",
        48 to "Кыргызстан",
        40 to "Казахстан",
        28 to "Грузия",
        16 to "Беларусь",
        113 to "Россия",
        1001 to "Другие регионы"
    )

    private val countryFlow: MutableStateFlow<Map<Int, String>> = MutableStateFlow(countryNameByIdMap)

    init {
        CoroutineScope(Dispatchers.IO).launch {
            setCountryFlow()
        }
    }

    private fun setCountryFlow() = flow<List<Country>> {
        try {
            val areas = searchVacanciesApi.getAllAreas()
            val countries = areas.map { it.mapToCountry() }
            debugLog(TAG) { "setCountryFlow: countries = ${countries.map { it.name }}" }
        } catch (e: Exception) {
            // В этом случае просто оставляем список стран пустым
            debugLog(TAG) { "Пусто" }
        }
    }

    override fun getCountryFlow(): StateFlow<Map<Int, String>> = countryFlow

    companion object {
        const val TAG = "CountryRepositoryFlowImpl"
    }
}

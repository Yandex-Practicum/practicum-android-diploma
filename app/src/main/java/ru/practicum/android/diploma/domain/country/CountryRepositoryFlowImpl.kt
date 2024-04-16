package ru.practicum.android.diploma.domain.country

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import okio.IOException
import ru.practicum.android.diploma.data.converter.AreaConverter.mapToCountry
import ru.practicum.android.diploma.data.network.SearchVacanciesApi
import ru.practicum.android.diploma.domain.debugLog

class CountryRepositoryFlowImpl(
    private val searchVacanciesApi: SearchVacanciesApi
) : CountryRepositoryFlow {

    private val countryNameByIdMap = mapOf(
        UZB_ID to "Узбекистан",
        AZE_ID to "Азербайджан",
        UA_ID to "Украина",
        KP_ID to "Кыргызстан",
        KZ_ID to "Казахстан",
        GEO_ID to "Грузия",
        BY_ID to "Беларусь",
        RU_ID to "Россия",
        OTHER_REGIONS_ID to "Другие регионы"
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
        } catch (e: IOException) {
            // В этом случае просто оставляем список стран пустым
            debugLog(TAG) { "Пусто" }
            throw e
        }
    }

    override fun getCountryFlow(): StateFlow<Map<Int, String>> = countryFlow

    companion object {
        const val TAG = "CountryRepositoryFlowImpl"
        const val UZB_ID = 97
        const val AZE_ID = 9
        const val UA_ID = 5
        const val KP_ID = 48
        const val KZ_ID = 40
        const val GEO_ID = 28
        const val BY_ID = 16
        const val RU_ID = 113
        const val OTHER_REGIONS_ID = 1001
    }
}

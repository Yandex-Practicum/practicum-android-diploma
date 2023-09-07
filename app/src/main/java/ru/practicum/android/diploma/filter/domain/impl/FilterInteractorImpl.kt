package ru.practicum.android.diploma.filter.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.domain.models.NetworkResponse
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.domain.models.Region
import ru.practicum.android.diploma.filter.ui.models.SelectedData
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class FilterInteractorImpl @Inject constructor(
    private val filterRepository: FilterRepository,
    private val searchRepository: SearchRepository,
    private val logger: Logger
) : FilterInteractor {

    override suspend fun getCountries(): Flow<NetworkResponse<List<Country>>> {
        logger.log(thisName, "getCountries(): Flow<NetworkResponse<List<Country>>>")
        return searchRepository.getCountries()
    }

    override suspend fun getRegions(query: String): Flow<NetworkResponse<List<Region>>> {
        logger.log(thisName, "getRegions($query: String): Flow<NetworkResponse<List<Region>>>")
        return searchRepository.getRegions(query)
    }

    // ==============================Shared preferences=============================================

    override suspend fun getSelectedData(key: String): SelectedData {
        return filterRepository.getSelectedData(key = key).also {
            logger.log(thisName, "getSelectedData($key: String): SelectedData=$it")
        }
    }

    override suspend fun saveRegion(key: String, region: Region) {
        val pair = Pair(region.name, region.area?.id ?: "")
        val stored = filterRepository.getSelectedData(key = key)
        val data = stored.copy(region = pair)
        filterRepository.saveCountry(key = key, selectedData = data)
        logger.log(thisName, "saveRegion($key: String, $pair: Region)")
    }

    override suspend fun saveCountry(key: String, country: Country) {
        val pair = Pair(country.name, country.id)
        val stored = filterRepository.getSelectedData(key = key)
        val data = stored.copy(country = pair)
        filterRepository.saveCountry(key = key, selectedData = data)
        logger.log(thisName, "saveCountry($key: String, $pair: Country)")
    }
}
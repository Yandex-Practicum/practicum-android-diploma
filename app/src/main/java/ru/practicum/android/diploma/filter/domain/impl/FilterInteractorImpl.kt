package ru.practicum.android.diploma.filter.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.domain.models.NetworkResponse
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.domain.models.Region
import ru.practicum.android.diploma.filter.ui.models.SelectedFilter
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class FilterInteractorImpl @Inject constructor(
    private val filterRepository: FilterRepository,
    private val searchRepository: SearchRepository,
    private val logger: Logger
) : FilterInteractor {

    // ================================Network======================================================
    override suspend fun getCountries(): Flow<NetworkResponse<List<Country>>> {
        logger.log(thisName, "getCountries(): Flow<NetworkResponse<List<Country>>>")
        return searchRepository.getCountries()
    }

    override suspend fun getRegions(query: String): Flow<NetworkResponse<List<Region>>> {
        logger.log(thisName, "getRegions($query: String): Flow<NetworkResponse<List<Region>>>")
        return searchRepository.getRegions(query)
    }

    // ==============================Shared preferences=============================================

    override suspend fun getSavedFilterSettings(key: String): SelectedFilter {
        return filterRepository.getSaveFilterSettings(key = key).also {
            logger.log(thisName, "getSelectedData($key: String): SelectedData=$it")
        }
    }

    override suspend fun saveRegion(key: String, region: Region) {
        val pair = Pair(region.name, region.area?.id ?: "")
        val stored = filterRepository.getSaveFilterSettings(key = key)
        val data = stored.copy(region = pair)
        filterRepository.saveSavedFilterSettings(key = key, selectedFilter = data)
        logger.log(thisName, "saveRegion($key: String, $pair: Region)")
    }

    override suspend fun saveCountry(key: String, country: Country) {
        val stored = filterRepository.getSaveFilterSettings(key = key)
        val data = stored.copy(country = country)
        filterRepository.saveSavedFilterSettings(key = key, selectedFilter = data)
        logger.log(thisName, "saveCountry($key: String, $country: Country)")
    }
}
package ru.practicum.android.diploma.filter.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.domain.models.NetworkResponse
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.domain.models.Region
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class FilterInteractorImpl @Inject constructor(
    private val filterRepository: FilterRepository,
    private val searchRepository: SearchRepository,
    private val logger: Logger
) : FilterInteractor {

    override fun filter() {}
    override suspend fun getCountries(): Flow<NetworkResponse<List<Country>>> {
        logger.log(thisName, "getCountries(): Flow<NetworkResponse<List<Country>>>")
        return searchRepository.getCountries()
    }

    override suspend fun getRegions(): Flow<NetworkResponse<List<Region>>>  {
        logger.log(thisName, "getRegions(): Flow<NetworkResponse<List<Region>>>")
        return searchRepository.getRegions()
    }

    override suspend fun getRegions(query: String): Flow<NetworkResponse<List<Region>>> {
        logger.log(thisName, "getRegions($query: String): Flow<NetworkResponse<List<Region>>>")
        return searchRepository.getRegions(query)
    }

    // ==============================Shared preferences=============================================

    override suspend fun getCountryFromPrefs(key: String): String {
        logger.log(thisName, "getCountriesFromPrefs($key: String): String?")
        return filterRepository.getStringFromPrefs(key = key)
    }

    override suspend fun getRegionFromPrefs(key: String): String {
        logger.log(thisName, "getRegionFromPrefs($key: String): String?")
        return filterRepository.getStringFromPrefs(key = key)
    }

    override suspend fun saveRegion(key: String, region: String) {
        logger.log(thisName, "saveRegion($key: String, $region: Region)")
        filterRepository.saveString(key = key, data = region)
    }

    override suspend fun saveCountry(key: String, country: String) {
        logger.log(thisName, "saveCountry($key: String, $country: String)")
        filterRepository.saveCountry(key = key, data = country)
    }
}
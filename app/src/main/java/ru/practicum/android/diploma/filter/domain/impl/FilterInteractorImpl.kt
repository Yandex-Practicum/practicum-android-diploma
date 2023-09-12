package ru.practicum.android.diploma.filter.domain.impl

import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.filter.domain.models.Region
import ru.practicum.android.diploma.filter.domain.models.SelectedFilter
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class FilterInteractorImpl @Inject constructor(
    private val filterRepository: FilterRepository,
    private val logger: Logger
) : FilterInteractor {

    override suspend fun getSavedFilterSettings(key: String): SelectedFilter {
        return filterRepository.getSaveFilterSettings(key = key).also {
            logger.log(thisName, "getSelectedData($key: String): SelectedData=$it")
        }
    }

    override suspend fun saveRegion(key: String, region: Region) {
        val stored = filterRepository.getSaveFilterSettings(key = key)
        val data = stored.copy(region = region)
        filterRepository.saveSavedFilterSettings(key = key, selectedFilter = data)
        logger.log(thisName, "saveRegion($key: String, $region: Region)")
    }

    override suspend fun refreshCountry(key: String, country: Country) {
        val stored = filterRepository.getSaveFilterSettings(key = key)
        val data = stored.copy(country = country, region = null)
        filterRepository.saveSavedFilterSettings(key = key, selectedFilter = data)
        logger.log(thisName, "saveCountry($key: String, $country: Country)")
    }

    override suspend fun saveIndustry(key: String, industry: Industry) {
        val stored = filterRepository.getSaveFilterSettings(key = key)
        val data = stored.copy(industry = industry)
        filterRepository.saveSavedFilterSettings(key = key, selectedFilter = data)
        logger.log(thisName, "saveRegion($key: String, $industry: Industry)")
    }

    override suspend fun refreshSalary(key: String, salary: String) {
        val stored = filterRepository.getSaveFilterSettings(key = key)
        val data = stored.copy(salary = salary)
        filterRepository.saveSavedFilterSettings(key = key, selectedFilter = data)
        logger.log(thisName, "refreshSalary($key: String, $salary: String)")
    }

    override suspend fun saveSavedData(key: String, data: SelectedFilter) {
        filterRepository.saveSavedFilterSettings(key = key, selectedFilter = data)
    }
}
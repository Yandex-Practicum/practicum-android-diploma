package ru.practicum.android.diploma.filter.domain.impl

import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.api.FilterRepository
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
    
    override suspend fun clearFilter(key: String) {
        filterRepository.saveFilterSettings(key, SelectedFilter.empty)
    }

    override suspend fun saveFilterSettings(key: String, data: SelectedFilter) {
        filterRepository.saveFilterSettings(key = key, selectedFilter = data)
        logger.log(thisName, "saveFilterSettings($key: String, $data: SelectedFilter)")
    }
}
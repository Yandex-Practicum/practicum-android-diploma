package ru.practicum.android.diploma.search.domain.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.search.domain.api.FiltersInteractor
import ru.practicum.android.diploma.search.domain.api.FiltersRepository
import ru.practicum.android.diploma.search.domain.model.FailureType
import ru.practicum.android.diploma.search.domain.model.Industry
import ru.practicum.android.diploma.search.domain.model.Resource

class FiltersInteractorImpl(private val filtersRepository: FiltersRepository) : FiltersInteractor {

    override fun getIndustries(): Flow<Pair<List<Industry>?, FailureType?>> {
        return filtersRepository.getIndustries().map { result ->
            when (result) {
                is Resource.Success -> Pair(result.data, null)
                is Resource.Failed -> Pair(null, result.message)
            }
        }
    }

    override fun saveFilters(industry: Industry?, salary: String?, onlyWithSalary: Boolean) {
        filtersRepository.saveFilters(industry, salary, onlyWithSalary)
    }

    override fun getSavedFilters(): Triple<Industry?, String?, Boolean> {
        return filtersRepository.getSavedFilters()
    }

    override fun clearSavedFilters() {
        filtersRepository.clearSavedFilters()
    }
}

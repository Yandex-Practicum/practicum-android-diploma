package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.api.SearchInteractor
import ru.practicum.android.diploma.domain.api.SearchRepository
import ru.practicum.android.diploma.domain.filter.FilterRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.filter.Filters
import ru.practicum.android.diploma.util.Resource

class SearchInteractorImpl(
    private val repository: SearchRepository,
    val filterRepository: FilterRepository
) : SearchInteractor {
    override fun loadVacancies(query: String, pageCount: Int): Flow<Pair<List<Vacancy>?, String?>> {
        val filters = Filters(
            filterRepository.getSelectedCountry(),
            filterRepository.getSelectedArea(),
            filterRepository.getSelectedIndustry(),
            filterRepository.getSalary(),
            filterRepository.getCheckedStatus()
        )
        return repository.searchVacancies(query, filters, pageCount).map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }

    override fun checkFilters(): Boolean {
        val filters =filterRepository.getCheckedStatus() ||
                filterRepository.getSelectedCountry() != null ||
                filterRepository.getSelectedArea() != null ||
                filterRepository.getSelectedIndustry() != null ||
                filterRepository.getSalary().isNotEmpty()
        return filters
    }
}
package ru.practicum.android.diploma.filters.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.filters.domain.api.FilterIndustriesInteractor
import ru.practicum.android.diploma.filters.domain.api.FilterIndustriesRepository
import ru.practicum.android.diploma.filters.domain.models.Industry
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.network.HttpStatusCode

class FilterIndustriesInteractorImpl(
    private val repository: FilterIndustriesRepository
) : FilterIndustriesInteractor {

    override fun getIndustries(): Flow<Pair<List<Industry>?, HttpStatusCode?>> {
        return repository.getIndustries().map { result ->
            when (result) {
                is Resource.Success -> Pair(result.data, result.httpStatusCode)
                is Resource.Error -> Pair(null, result.httpStatusCode)
            }
        }
    }
}

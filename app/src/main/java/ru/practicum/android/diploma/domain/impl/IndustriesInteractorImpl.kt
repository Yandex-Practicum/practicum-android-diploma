package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.filters.IndustriesInteractor
import ru.practicum.android.diploma.domain.filters.IndustriesRepository
import ru.practicum.android.diploma.domain.models.Industries
import ru.practicum.android.diploma.util.Resource

class IndustriesInteractorImpl(
    private val repository: IndustriesRepository
) : IndustriesInteractor {
    override fun getIndustries(): Flow<Pair<List<Industries>?, Int?>> {
        return repository.getIndustries().map { result ->
            when (result) {
                is Resource.Success -> Pair(result.data, null)
                is Resource.Error -> Pair(null, result.error)
            }
        }
    }
}

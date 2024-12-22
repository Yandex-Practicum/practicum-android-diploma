package ru.practicum.android.diploma.domain.industries

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.data.dto.IndustriesResponse
import ru.practicum.android.diploma.data.dto.industries.IndustriesRepository
import ru.practicum.android.diploma.util.Resource

class IndustriesInteractorImpl(private val repository: IndustriesRepository) : IndustriesInteractor {

    override fun getIndustries(): Flow<Pair<IndustriesResponse?, String?>> {
        return repository.getIndustries().map { result ->
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

}

package ru.practicum.android.diploma.domain.industries.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.data.response.Industries
import ru.practicum.android.diploma.domain.industries.IndustriesInteractor
import ru.practicum.android.diploma.domain.industries.IndustriesRepository
import ru.practicum.android.diploma.util.Resource

class IndustriesInteractorImpl(
    val industriesRepository: IndustriesRepository
) : IndustriesInteractor {
    override fun searchIndustries(industries: String): Flow<Pair<List<Industries>?, Int?>> {
        return industriesRepository.searchIndustries(industries).map { resource ->
            when (resource) {
                is Resource.Success -> Pair(resource.data, null)
                is Resource.Error -> Pair(null, resource.message)
            }
        }
    }
}

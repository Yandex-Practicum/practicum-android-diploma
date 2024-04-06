package ru.practicum.android.diploma.domain.industries

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.models.industries.ChildIndustry
import ru.practicum.android.diploma.util.ResourceContentSearch

class IndustriesInteractorImpl(
    private val industriesRepository: IndustriesRepository
) : IndustriesInteractor {
    override fun getIndustries(): Flow<Pair<List<ChildIndustry>?, Int?>> =
        industriesRepository.getIndustries().map { resource ->
            when (resource) {

                is ResourceContentSearch.SuccessSearch
                -> Pair(resource.data, null)

                is ResourceContentSearch.ErrorSearch
                -> Pair(null, resource.message)

                is ResourceContentSearch.ServerErrorSearch
                -> Pair(null, SERVER_ERROR_CODE)
            }
        }

    companion object {
        const val SERVER_ERROR_CODE = 500
    }
}

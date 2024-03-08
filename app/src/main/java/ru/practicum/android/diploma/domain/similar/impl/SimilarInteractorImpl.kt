package ru.practicum.android.diploma.domain.similar.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.api.SimilarRepository
import ru.practicum.android.diploma.domain.models.main.SearchingVacancies
import ru.practicum.android.diploma.domain.similar.SimilarInteractor
import ru.practicum.android.diploma.util.Resource

class SimilarInteractorImpl(
    private val similarRepository: SimilarRepository
) : SimilarInteractor {

    override fun searchSimilarVacancies(vacancyId: String): Flow<Pair<SearchingVacancies?, Int?>> {
        return similarRepository.searchSimilarVacancies(vacancyId).map { resource ->
            when (resource) {
                is Resource.Success -> Pair(resource.data, null)
                is Resource.Error -> Pair(null, resource.message)
            }
        }
    }
}

package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.api.DetailInteractor
import ru.practicum.android.diploma.domain.api.DetailRepository
import ru.practicum.android.diploma.domain.models.detail.VacancyDetail
import ru.practicum.android.diploma.util.Resource

class DetailInteractorImpl(
    private val detailRepository: DetailRepository
) : DetailInteractor {

    override fun searchDetailInformation(vacancyId: String): Flow<Pair<VacancyDetail?, Int?>> {
        return detailRepository.searchDetailInformation(vacancyId).map { resource ->
            when (resource) {
                is Resource.Success -> Pair(resource.data, null)
                is Resource.Error -> Pair(null, resource.message)
            }
        }
    }
}

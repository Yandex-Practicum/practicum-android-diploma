package ru.practicum.android.diploma.search.domain.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.search.domain.model.FailureType
import ru.practicum.android.diploma.search.domain.model.Resource
import ru.practicum.android.diploma.search.domain.model.VacancyPreview

class SearchInteractorImpl(private val searchRepository: SearchRepository) : SearchInteractor {

    override fun getVacancies(text: String, area: String?):
        Flow<Pair<List<VacancyPreview>?, FailureType?>> {
        return searchRepository.getVacancies(text, area).map { result ->
            when (result) {
                is Resource.Success -> {
                    val previewData = result.data
                    Pair(previewData, null)
                }

                is Resource.Failed -> {
                    Pair(null, result.message)
                }
            }
        }
    }
}

package ru.practicum.android.diploma.search.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.data.dto.SearchRequest
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.search.domain.models.VacanciesResponse

class SearchInteractorImpl(private val searchRepository: SearchRepository) : SearchInteractor {
    override fun search(options: SearchRequest): Flow<VacanciesResponse> {
        return searchRepository.search(options)
    }
}

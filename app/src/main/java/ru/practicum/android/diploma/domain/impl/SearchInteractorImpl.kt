package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.search.SearchInteractor
import ru.practicum.android.diploma.domain.api.search.SearchRepository
import ru.practicum.android.diploma.domain.models.VacancyPage

class SearchInteractorImpl(private val searchRepository: SearchRepository) : SearchInteractor {
    override fun searchVacancies(options: Map<String, String>): Flow<Result<VacancyPage>> {
        return searchRepository.searchRequest(options)
    }
}

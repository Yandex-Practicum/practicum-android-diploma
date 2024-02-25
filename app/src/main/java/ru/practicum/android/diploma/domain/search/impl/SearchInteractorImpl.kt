package ru.practicum.android.diploma.domain.search.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.search.network.Resource
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.search.SearchInteractor
import ru.practicum.android.diploma.domain.search.SearchRepository

class SearchInteractorImpl(private val searchRepository: SearchRepository): SearchInteractor {
    override suspend fun search(expression: String, page: Int): Resource<List<Vacancy>> {
        return searchRepository.search(expression,page)
    }

}

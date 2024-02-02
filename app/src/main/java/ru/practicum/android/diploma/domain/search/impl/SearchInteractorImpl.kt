package ru.practicum.android.diploma.domain.search.impl

import androidx.paging.PagingSource
import ru.practicum.android.diploma.data.search.SearchRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.search.SearchInteractor

class SearchInteractorImpl(private val searchRepository: SearchRepository): SearchInteractor {
    override fun searchJobs(query: String, pageSize: Int): PagingSource<Int, Vacancy> {
        return searchRepository.searchJobs(query, pageSize)
    }

}

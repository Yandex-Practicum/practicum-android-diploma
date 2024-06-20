package ru.practicum.android.diploma.domain.search.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.data.search.SearchRepository
import ru.practicum.android.diploma.domain.search.SearchInteractor
import ru.practicum.android.diploma.domain.search.models.DomainVacancy
import ru.practicum.android.diploma.util.Resource

class SearchInteractorImpl(private val repository: SearchRepository) : SearchInteractor {
    override var currentPage: Int? = null
    override var foundItems: Int? = null
    override var pages: Int? = null
    override fun searchVacancies(text: String, page: Int): Flow<Pair<List<DomainVacancy>?, String?>> {
        return repository.searchVacancies(text, page).map { result ->
            when (result) {
                is Resource.Success -> {
                    currentPage = repository.currentPage
                    foundItems = repository.foundItems
                    pages = repository.pages
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }
}

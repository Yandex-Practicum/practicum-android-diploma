package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.api.SearchInteractor
import ru.practicum.android.diploma.domain.api.SearchRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.Resource

class SearchInteractorImpl(private val repository: SearchRepository) : SearchInteractor {
    override var currentPage: Int? = null
    override var foundItems: Int? = null
    override var pages: Int? = null
    override fun searchVacancies(text: String, page: Int): Flow<Pair<List<Vacancy>?, String?>> {
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
    override suspend fun getDetails(id: String): Resource<Vacancy> {
        return repository.getDetails(id)
    }
}

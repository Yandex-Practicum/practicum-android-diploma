package ru.practicum.android.diploma.search.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.Resource

class SearchInteractorImpl(private val repository: SearchRepository) : SearchInteractor {
    override fun loadVacancies(expression: String): Flow<Pair<List<Vacancy>?, String?>> {
        return repository.searchVacancies(expression).map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }
}
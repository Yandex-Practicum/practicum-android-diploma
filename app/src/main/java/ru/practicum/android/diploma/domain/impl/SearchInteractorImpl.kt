package ru.practicum.android.diploma.domain.impl


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.Resource
import ru.practicum.android.diploma.domain.SearchInteractor
import ru.practicum.android.diploma.domain.SearchRepository
import ru.practicum.android.diploma.domain.models.ErrorNetwork
import ru.practicum.android.diploma.domain.models.Vacancy

class SearchInteractorImpl(private val repository: SearchRepository) : SearchInteractor {
    override fun search(expression: String): Flow<Pair<List<Vacancy>?, ErrorNetwork?>> {

        return repository.search(expression).map { result ->
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

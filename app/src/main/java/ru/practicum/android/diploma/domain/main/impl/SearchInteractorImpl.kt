package ru.practicum.android.diploma.domain.main.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.domain.main.SearchRepository
import ru.practicum.android.diploma.domain.main.SearchInteractor
import ru.practicum.android.diploma.domain.models.Vacancy

class SearchInteractorImpl(
    val repository: SearchRepository
) : SearchInteractor {

    override suspend fun searchTrack(queryMap: Map<String, String>): Flow<Pair<List<Vacancy>?, Int?>> {
        return repository.makeRequest(queryMap).map {
            when (it) {
                is Resource.Success -> {
                    Pair(it.data, null)
                }

                is Resource.Error -> {
                    Pair(null, it.message)
                }
            }
        }
    }
}

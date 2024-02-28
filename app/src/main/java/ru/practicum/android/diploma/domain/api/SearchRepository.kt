package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.main.SearchingVacancies

interface SearchRepository {
    fun makeRequest(queryMap: Map<String, String>): Flow<Resource<SearchingVacancies>>

    suspend fun vacanciesPagination(query: String, nextPage: Int): Resource<SearchingVacancies>
}

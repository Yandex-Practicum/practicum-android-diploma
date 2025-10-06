package ru.practicum.android.diploma.domain.repository

import ru.practicum.android.diploma.domain.models.SearchResult
import ru.practicum.android.diploma.util.Resource

interface DataRepository {
    suspend fun searchVacancies(query: String, page: Int): Resource<SearchResult>
}

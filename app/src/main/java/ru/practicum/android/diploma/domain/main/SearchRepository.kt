package ru.practicum.android.diploma.domain.main

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.Resource

interface SearchRepository {
    fun makeRequest(queryMap: Map<String, String>): Flow<Resource<List<Vacancy>>>
}

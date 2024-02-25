package ru.practicum.android.diploma.domain.search

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.search.network.Resource
import ru.practicum.android.diploma.domain.models.Vacancy

interface  SearchRepository {
    suspend fun search(
        expression: String,
        page: Int,
    ): Resource<List<Vacancy>>
}

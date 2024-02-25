package ru.practicum.android.diploma.domain.search

import ru.practicum.android.diploma.data.search.network.Resource
import ru.practicum.android.diploma.domain.models.Vacancy

interface SearchInteractor {
    suspend fun search(
        expression: String,
        page: Int,
    ): Resource<List<Vacancy>>
}

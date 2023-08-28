package ru.practicum.android.diploma.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.FetchResult

interface SearchVacanciesUseCase {
    suspend fun search(query: String): Flow<FetchResult>
}
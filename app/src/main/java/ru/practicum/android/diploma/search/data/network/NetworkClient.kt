package ru.practicum.android.diploma.search.data.network

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.FetchResult

interface NetworkClient {
    suspend fun doRequest(any: Any): Flow<FetchResult>
}
package ru.practicum.android.diploma.details.data.network

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.FetchResult
interface RemoteDataSource {
    suspend fun getVacancyFullInfo(id: String): Flow<FetchResult>
}
package ru.practicum.android.diploma.details.data.network

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.data.network.VacancyRequest
import ru.practicum.android.diploma.search.domain.models.FetchResult
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val networkClient: NetworkClient) :
    RemoteDataSource {
    override suspend fun getVacancyFullInfo(id: Long): Flow<FetchResult> {
        val request = VacancyRequest.FullInfoRequest(id)
        return networkClient.doRequest(request)
    }
}
package ru.practicum.android.diploma.details.data.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.data.network.Vacancy
import ru.practicum.android.diploma.search.data.network.converter.VacancyModelConverter
import ru.practicum.android.diploma.search.data.network.dto.response.VacanciesSearchCodeResponse
import ru.practicum.android.diploma.search.domain.models.FetchResult
import ru.practicum.android.diploma.search.domain.models.NetworkError
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val networkClient: NetworkClient,
    private val converter: VacancyModelConverter,
) : RemoteDataSource {
    
    override suspend fun getVacancyFullInfo(id: String): Flow<FetchResult> {
        val request = Vacancy.FullInfoRequest(id)
        
        //поменять на свои данные!!!
        val result = (networkClient.doRequest(request) as VacanciesSearchCodeResponse).items
        return if (!result.isNullOrEmpty()) {
            flowOf(FetchResult.Success(converter.mapList(result)))
        } else flowOf(FetchResult.Error(NetworkError.SEARCH_ERROR))
    }
}
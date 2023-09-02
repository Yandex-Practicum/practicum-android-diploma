package ru.practicum.android.diploma.search.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.data.network.VacancyRequest
import ru.practicum.android.diploma.search.data.network.converter.VacancyModelConverter
import ru.practicum.android.diploma.search.data.network.dto.VacanciesSearchResponse
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.search.domain.models.FetchResult
import ru.practicum.android.diploma.search.domain.models.NetworkError
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val networkClient: NetworkClient,
    private val converter: VacancyModelConverter,
    private val logger: Logger,
) : SearchRepository {
    
    override suspend fun search(query: String): Flow<FetchResult> {
        logger.log(thisName, "fun search($query: String): Flow<FetchResult>")
        
        val request = VacancyRequest.SearchVacanciesRequest(query)
        val response = networkClient.doRequest(request)
        
        return when (response.resultCode) {
            in 100..399 -> {
                val resultList = (response as VacanciesSearchResponse).items
                if (resultList.isNullOrEmpty()) {
                    flowOf(FetchResult.Error(NetworkError.SEARCH_ERROR))
                } else {
                    val vacancies = converter.mapList(resultList)
                    val count = response.found ?: 0
                    return flowOf(FetchResult.Success(data = vacancies, count = count))
                }
            }
            
            in 400..499 -> {
                flowOf(FetchResult.Error(NetworkError.SEARCH_ERROR))
            }
            
            else -> {
                flowOf(FetchResult.Error(NetworkError.CONNECTION_ERROR))
            }
        }
    }
}
package ru.practicum.android.diploma.search.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.practicum.android.diploma.common.data.Mapper
import ru.practicum.android.diploma.common.data.dto.SearchVacancyRequest
import ru.practicum.android.diploma.common.data.dto.SearchVacancyResponse
import ru.practicum.android.diploma.common.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.search.domain.model.SearchQueryParams
import ru.practicum.android.diploma.search.domain.model.SearchViewState
import ru.practicum.android.diploma.search.domain.repository.SearchRepository

class SearchRepositoryImpl(
    private val networkClient: RetrofitNetworkClient,
    private val mapper: Mapper,
    ) : SearchRepository {

    override suspend fun searchVacancy(expression: SearchQueryParams): Flow<SearchViewState> = flow {

        val response = networkClient.doRequest(SearchVacancyRequest(expression))
        when (response.resultCode) {
            200 ->{
                val result = (response as SearchVacancyResponse).items
                if (result.isEmpty()){
                    emit(SearchViewState.NotFoundError)
                }else{
                    val data = response.items.map {
                        mapper.map(it as SearchVacancyResponse)
                    }
                    emit(SearchViewState.Success(data))
                }
            }
            400 ->{
                emit(SearchViewState.NotFoundError)
            }else ->{
            emit(SearchViewState.ServerError)
        }
        }
    }.flowOn(Dispatchers.IO)


}

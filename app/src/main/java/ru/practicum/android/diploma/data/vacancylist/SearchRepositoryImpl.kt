package ru.practicum.android.diploma.data.vacancylist

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.ResponseCodes
import ru.practicum.android.diploma.data.vacancylist.dto.VacanciesSearchRequest
import ru.practicum.android.diploma.data.vacancylist.dto.VacanciesSearchResponse
import ru.practicum.android.diploma.domain.api.Resource
import ru.practicum.android.diploma.domain.api.SearchRepository

class SearchRepositoryImpl(
    private val networkClient: NetworkClient,
) : SearchRepository {
    override fun makeRequest(request: VacanciesSearchRequest): Flow<Resource<VacanciesSearchResponse>> = flow{
        val response = networkClient.doRequest(request)
        when(response.resultCode){
            ResponseCodes.SUCCESS -> emit(Resource.Content())
            ResponseCodes.ERROR -> emit(Resource.Error(response.resultCode.code))
            ResponseCodes.NO_CONNECTION -> emit(Resource.NoConnection(response.resultCode.code))
        }
    }
}

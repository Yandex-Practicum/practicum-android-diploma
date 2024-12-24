package ru.practicum.android.diploma.data.vacancy.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.dto.VacancyRequest
import ru.practicum.android.diploma.data.dto.VacancyResponse
import ru.practicum.android.diploma.data.dto.model.VacancyFullItemDto
import ru.practicum.android.diploma.data.dto.network.RetrofitNetworkClient
import ru.practicum.android.diploma.data.vacancy.VacancyRepository
import ru.practicum.android.diploma.domain.NetworkClient
import ru.practicum.android.diploma.ui.search.state.VacancyError
import ru.practicum.android.diploma.util.Resource

class VacancyRepositoryImpl(
    private val networkClient: NetworkClient
) : VacancyRepository {

    override fun getVacancyId(id: String): Flow<Resource<VacancyFullItemDto>> = flow {
        val response = networkClient.doRequest(VacancyRequest(id))
        when (response.code) {

            RetrofitNetworkClient.INTERNET_NOT_CONNECT -> {
                emit(Resource.Error(VacancyError.NETWORK_ERROR))
            }
            RetrofitNetworkClient.HTTP_BAD_REQUEST_CODE -> {
                emit(Resource.Error(VacancyError.BAD_REQUEST))
            }
            RetrofitNetworkClient.HTTP_PAGE_NOT_FOUND_CODE -> {
                emit(Resource.Error(VacancyError.NOT_FOUND))
            }
            RetrofitNetworkClient.HTTP_INTERNAL_SERVER_ERROR_CODE -> {
                emit(Resource.Error(VacancyError.SERVER_ERROR))
            }
            RetrofitNetworkClient.HTTP_OK_CODE -> {
                with(response as VacancyResponse) {
                    emit(Resource.Success(response.items))
                }
            }
            else -> {
                emit(Resource.Error(VacancyError.UNKNOWN_ERROR))
            }
        }
    }
}


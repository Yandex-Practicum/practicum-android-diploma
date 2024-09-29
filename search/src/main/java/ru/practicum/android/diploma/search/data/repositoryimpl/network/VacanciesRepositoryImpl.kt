package ru.practicum.android.diploma.search.data.repositoryimpl.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.commonutils.Resource
import ru.practicum.android.diploma.data.networkclient.api.NetworkClient
import ru.practicum.android.diploma.data.networkclient.api.dto.HHApiIndustriesRequest
import ru.practicum.android.diploma.data.networkclient.api.dto.HHApiRegionsRequest
import ru.practicum.android.diploma.data.networkclient.api.dto.HHApiVacanciesRequest
import ru.practicum.android.diploma.data.networkclient.api.dto.HHVacanciesResponse
import ru.practicum.android.diploma.data.networkclient.api.dto.HttpStatus
import ru.practicum.android.diploma.search.domain.Vacancy
import ru.practicum.android.diploma.search.domain.repository.VacanciesRepository

class VacanciesRepositoryImpl(private val networkClient: NetworkClient) : VacanciesRepository {
    override fun searchVacancies(options: Map<String, String>): Flow<Resource<List<Vacancy>>> = flow {
        val response = networkClient.doRequest(
            HHApiVacanciesRequest(
                "term"
            )
        )
        when (response.resultCode) {
            HttpStatus.NO_INTERNET -> {
                emit(Resource.Error("Check network connection"))
            }

            HttpStatus.OK -> {
                with(response as HHVacanciesResponse) {
                    val data = response.items
                    emit(Resource.Success(data))
                }
            }

            HttpStatus.CLIENT_ERROR -> {
                emit(Resource.Error("Request was not accepted ${response.resultCode}"))
            }

            HttpStatus.SERVER_ERROR -> {
                emit(Resource.Error("Unexpcted error ${response.resultCode}"))
            }
        }
    }

    override fun listVacancy(id: String): Flow<Unit> = flow {
        val response = networkClient.doRequest(
            HHApiVacanciesRequest(
                id
            )
        )
    }

    override fun listAreas(): Flow<Unit> = flow {
        val response = networkClient.doRequest(
            HHApiRegionsRequest(
                "term"
            )
        )
    }

    override fun listIndustries(): Flow<Unit> = flow {
        val response = networkClient.doRequest(
            HHApiIndustriesRequest(
                "term"
            )
        )
    }

    companion object {
        private const val TAG = "VacanciesRepositoryImpl"
    }
}

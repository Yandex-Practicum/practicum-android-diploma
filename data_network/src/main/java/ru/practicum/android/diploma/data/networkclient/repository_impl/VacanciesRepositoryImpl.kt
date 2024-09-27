package ru.practicum.android.diploma.data.networkclient.repository_impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.networkclient.rest_api.NetworkClient
import ru.practicum.android.diploma.data.networkclient.rest_api.dto.HHApiIndustriesRequest
import ru.practicum.android.diploma.data.networkclient.rest_api.dto.HHApiRegionsRequest
import ru.practicum.android.diploma.data.networkclient.rest_api.dto.HHApiVacanciesRequest
import ru.practicum.android.diploma.search.domain.repository.VacanciesRepository

class VacanciesRepositoryImpl(private val networkClient: NetworkClient) : VacanciesRepository {
    override fun searchVacancies(options: Map<String, String>): Flow<Unit> = flow {
        val response = networkClient.doRequest(
            HHApiVacanciesRequest(
                "term"
            )
        )
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

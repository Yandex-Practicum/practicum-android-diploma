package ru.practicum.android.diploma.networkclient.data

import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.networkclient.data.dto.HHApiIndustriesRequest
import ru.practicum.android.diploma.networkclient.data.dto.HHApiRegionsRequest
import ru.practicum.android.diploma.networkclient.data.dto.HHApiVacanciesRequest
import ru.practicum.android.diploma.networkclient.data.dto.HHVacanciesResponse
import ru.practicum.android.diploma.networkclient.domain.api.VacanciesRepository

class VacanciesRepositoryImpl(private val networkClient: NetworkClient) : VacanciesRepository {
    override fun searchVacancies(options: Map<String, String>) = flow {
        val response = networkClient.doRequest(HHApiVacanciesRequest("term"))
    }

    override fun listVacancy(id: String) = flow {
        val response = networkClient.doRequest(HHApiVacanciesRequest(id))
    }

    override fun listAreas() = flow {
        val response = networkClient.doRequest(HHApiRegionsRequest("term"))
    }

    override fun listIndustries() = flow {
        val response = networkClient.doRequest(HHApiIndustriesRequest("term"))
    }

    companion object {
        private const val TAG = "VacanciesRepositoryImpl"
    }
}

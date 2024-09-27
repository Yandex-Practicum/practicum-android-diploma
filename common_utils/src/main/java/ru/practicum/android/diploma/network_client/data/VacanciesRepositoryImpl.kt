package ru.practicum.android.diploma.network_client.data

import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.network_client.domain.api.VacanciesRepository
import ru.practicum.android.diploma.network_client.domain.models.Resource

class VacanciesRepositoryImpl(private val networkClient: NetworkClient) : VacanciesRepository {
    override fun searchVacancies(dto: Any) = flow {
        val response = networkClient.doRequest()
        when (response.resultCode) {
            -1 -> emit(Resource.Error(""))
        }
    }

    override suspend fun listVacancy(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun listAreas() {
        TODO("Not yet implemented")
    }

    override suspend fun listIndustries() {
        TODO("Not yet implemented")
    }
}

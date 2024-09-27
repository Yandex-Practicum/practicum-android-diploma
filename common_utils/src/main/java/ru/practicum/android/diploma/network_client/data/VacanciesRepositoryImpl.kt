package ru.practicum.android.diploma.network_client.data

import android.util.Log
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.network_client.data.dto.Response
import ru.practicum.android.diploma.network_client.domain.api.VacanciesRepository
import ru.practicum.android.diploma.network_client.domain.models.Resource

class VacanciesRepositoryImpl(private val networkClient: NetworkClient) : VacanciesRepository {
    override fun searchVacancies(dto: Any) = flow {
        if (false) {
            Log.e(TAG, "No network")
            emit {
                object : Response {
                    override var resultCode: Int = -1
                }
            }
        }
        val response = networkClient.doRequest()
        when (response.resultCode) {
            -1 -> emit(Resource.Error(""))
        }
    }

    override fun listVacancy(id: String) {
        TODO("Not yet implemented")
    }

    override fun listAreas() {
        TODO("Not yet implemented")
    }

    override fun listIndustries() {
        TODO("Not yet implemented")
    }

    companion object {
        private const val TAG = "VacanciesRepositoryImpl"
    }
}

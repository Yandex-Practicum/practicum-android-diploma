package ru.practicum.android.diploma.network_client.data

import android.util.Log
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.network_client.data.dto.Response
import ru.practicum.android.diploma.network_client.domain.api.VacanciesRepository
import ru.practicum.android.diploma.network_client.domain.models.Resource

class VacanciesRepositoryImpl(private val networkClient: NetworkClient) : VacanciesRepository {
    override fun searchVacancies(options: Map<String, String>) {
        TODO()
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

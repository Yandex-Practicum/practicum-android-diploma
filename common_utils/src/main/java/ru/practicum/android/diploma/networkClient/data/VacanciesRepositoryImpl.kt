package ru.practicum.android.diploma.networkClient.data

import ru.practicum.android.diploma.networkClient.domain.api.VacanciesRepository

class VacanciesRepositoryImpl(private val networkClient: NetworkClient) : VacanciesRepository {
    override fun searchVacancies(options: Map<String, String>) {
    }

    override fun listVacancy(id: String) {
    }

    override fun listAreas() {
    }

    override fun listIndustries() {
    }

    companion object {
        private const val TAG = "VacanciesRepositoryImpl"
    }
}

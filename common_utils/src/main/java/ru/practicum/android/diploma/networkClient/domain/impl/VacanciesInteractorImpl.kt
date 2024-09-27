package ru.practicum.android.diploma.networkClient.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.networkClient.domain.api.VacanciesInteractor
import ru.practicum.android.diploma.networkClient.domain.api.VacanciesRepository

class VacanciesInteractorImpl(private val repository: VacanciesRepository) : VacanciesInteractor {
    override fun searchVacancies(options: Map<String, String>): Flow<String> {
        TODO("Not yet implemented")
    }

    override fun listVacancy(id: String): Flow<String> {
        TODO("Not yet implemented")
    }

    override fun listAreas(): Flow<String> {
        TODO("Not yet implemented")
    }

    override fun listIndustries(): Flow<String> {
        TODO("Not yet implemented")
    }
}

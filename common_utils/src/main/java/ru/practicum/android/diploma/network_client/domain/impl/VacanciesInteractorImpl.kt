package ru.practicum.android.diploma.network_client.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.network_client.domain.api.VacanciesInteractor
import ru.practicum.android.diploma.network_client.domain.api.VacanciesRepository
import ru.practicum.android.diploma.network_client.domain.models.Resource

class VacanciesInteractorImpl(private val repository: VacanciesRepository) : VacanciesInteractor {
    override fun searchVacancies(options: Map<String, String>): Flow<Pair<List<Vacancies>, String?>> {
        return repository.searchVacancies(options).map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
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

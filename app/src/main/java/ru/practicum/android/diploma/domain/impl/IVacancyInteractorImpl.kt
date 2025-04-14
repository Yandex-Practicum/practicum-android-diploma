package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.IVacancyInteractor
import ru.practicum.android.diploma.domain.api.IVacancyRepository
import ru.practicum.android.diploma.domain.api.Resource

class IVacancyInteractorImpl(repository: IVacancyRepository) : IVacancyInteractor {
    override suspend fun searchVacancies(expression: String): Flow<Pair<List<Vacancy>?, String?>> {
        return repository.searchVacancies(expression).map { result ->
            when (result) {
                is Resource.Error -> {
                    Pair(null, result.message)
                }

                is Resource.Success -> {
                    Pair(result.data, null)
                }
            }
        }
    }

    override suspend fun getCountries(expression: String): Flow<Pair<List<Area>?, String?>> {
        return repository.getContries(expression).map { result ->
            when (result) {
                is Resource.Error -> {
                    Pair(null, result.message)
                }

                is Resource.Success -> {
                    Pair(result.data, null)
                }
            }
        }
    }

    override suspend fun getRegion(expression: String): Flow<Pair<List<Area>?, String?>> {
        return repository.getRegion(expression).map { result ->
            when (result) {
                is Resource.Error -> {
                    Pair(null, result.message)
                }

                is Resource.Success -> {
                    Pair(result.data, null)
                }
            }
        }
    }

    override suspend fun getIndustries(expression: String): Flow<Pair<List<Industry>?, String?>> {
        return repository.getIndustries(expression).map { result ->
            when (result) {
                is Resource.Error -> {
                    Pair(null, result.message)
                }

                is Resource.Success -> {
                    Pair(result.data, null)
                }
            }
        }
    }

    override suspend fun getVacancyDetails(expression: String): Flow<Pair<List<VacancyDetails>?, String?>> {
        return repository.getVacancyDetails(expression).map { result ->
            when (result) {
                is Resource.Error -> {
                    Pair(null, result.message)
                }

                is Resource.Success -> {
                    Pair(result.data, null)
                }
            }
        }
    }
}

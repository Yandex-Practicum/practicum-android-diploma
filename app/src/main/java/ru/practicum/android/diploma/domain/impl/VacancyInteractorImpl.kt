package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.api.IVacancyInteractor
import ru.practicum.android.diploma.domain.api.IVacancyRepository
import ru.practicum.android.diploma.domain.api.Resource
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetails

class VacancyInteractorImpl(private val repository: IVacancyRepository) : IVacancyInteractor {
    override fun searchVacancies(expression: String): Flow<Pair<List<Vacancy>?, String?>> {
        return repository.searchVacancies(expression).map { result ->
            when (result) {
                is Resource.Error -> {
                    Pair(null, result.message)
                }

                is Resource.Success -> {
                    Pair(result.data?.items?.toList(), null)
                }
            }
        }
    }

    override fun getCountries(): Flow<Pair<List<Area>?, String?>> {
        return repository.getCountries().map { result ->
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

    override fun getIndustries(): Flow<Pair<List<Industry>?, String?>> {
        return repository.getIndustries().map { result ->
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

    override fun getVacancyDetails(vacancyId: String): Flow<Pair<VacancyDetails?, String?>> {
        return repository.getVacancyDetails(vacancyId).map { result ->
            when (result) {
                is Resource.Error -> {
                    Pair(null, result.message)
                }

                is Resource.Success -> {
                    if (result.data == null) {
                        Pair(null, result.message)
                    } else {
                        Pair(result.data, null)
                    }
                }
            }
        }
    }
}

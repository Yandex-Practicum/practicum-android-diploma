package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.dto.Area
import ru.practicum.android.diploma.data.dto.Industry
import ru.practicum.android.diploma.data.dto.Vacancy
import ru.practicum.android.diploma.data.dto.VacancyDetails
import ru.practicum.android.diploma.data.network.dto.GetVacancyDetailsRequest
import ru.practicum.android.diploma.data.network.dto.SearchVacanciesRequest
import ru.practicum.android.diploma.domain.api.IVacancyInteractor
import ru.practicum.android.diploma.domain.api.IVacancyRepository
import ru.practicum.android.diploma.domain.api.Resource

class VacancyInteractorImpl(private val repository: IVacancyRepository) : IVacancyInteractor {
    override fun searchVacancies(expression: String): Flow<Pair<List<Vacancy>?, String?>> = flow {
        repository.searchVacancies(SearchVacanciesRequest(text = expression, page = 0)).collect { result ->
            when (result) {
                is Resource.Error -> {
                    emit(Pair(null, result.message))
                }

                is Resource.Success -> {
                    emit(Pair(result.data?.items?.toList(), null))
                }
            }
        }
    }

    override fun getCountries(): Flow<Pair<List<Area>?, String?>> = flow {
        repository.getCountries().collect() { result ->
            when (result) {
                is Resource.Error -> {
                    emit(Pair(null, result.message))
                }

                is Resource.Success -> {
                    Pair(result.data, null)
                }
            }
        }
    }

    override fun getIndustries(): Flow<Pair<List<Industry>?, String?>> = flow {
        repository.getIndustries().collect { result ->
            when (result) {
                is Resource.Error -> {
                    emit(Pair(null, result.message))
                }

                is Resource.Success -> {
                    emit(Pair(result.data, null))
                }
            }
        }
    }

    override fun getVacancyDetails(vacancyId: String): Flow<Pair<VacancyDetails?, String?>> = flow {
        repository.getVacancyDetails(GetVacancyDetailsRequest(vacancyId)).collect { result ->
            when (result) {
                is Resource.Error -> {
                    emit(Pair(null, result.message))
                }

                is Resource.Success -> {
                    if (result.data == null) {
                        emit(Pair(null, result.message))
                    } else {
                        emit(Pair(result.data, null))
                    }
                }
            }
        }
    }
}

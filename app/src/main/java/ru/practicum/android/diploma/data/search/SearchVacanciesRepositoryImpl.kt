package ru.practicum.android.diploma.data.search

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.practicum.android.diploma.data.dto.VacancyRequest
import ru.practicum.android.diploma.data.dto.VacancyResponse
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.NetworkCodes
import ru.practicum.android.diploma.domain.api.SearchVacanciesInteractor.Resource
import ru.practicum.android.diploma.domain.api.SearchVacanciesRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.data.search.VacancyDtoMapper

class SearchVacanciesRepositoryImpl(private val networkClient: NetworkClient) : SearchVacanciesRepository {
    override suspend fun searchVacancies(expression: String): Flow<Resource<List<Vacancy>>> =
        flow {
            val response = networkClient.doRequest(VacancyRequest(expression))
            when (response.resultCode) {
                NetworkCodes.SUCCESS_CODE -> {
                    val vacanciesResponse = response as VacancyResponse
                    val data = VacancyDtoMapper.mapList(vacanciesResponse.vacancies)
                    emit(Resource.Success(data))
                }

                NetworkCodes.NO_NETWORK_CODE -> {
                    emit(Resource.Error(NetworkCodes.NO_NETWORK_CODE))
                }

                else -> {
                    emit(Resource.Error(response.resultCode))
                }
            }
        }.flowOn(Dispatchers.IO)
}

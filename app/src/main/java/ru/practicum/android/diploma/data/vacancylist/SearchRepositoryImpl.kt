package ru.practicum.android.diploma.data.vacancylist

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.ResponseCodes
import ru.practicum.android.diploma.data.converters.VacancyConverter.toVacancy
import ru.practicum.android.diploma.data.vacancylist.dto.VacanciesSearchRequest
import ru.practicum.android.diploma.data.vacancylist.dto.VacanciesSearchResponse
import ru.practicum.android.diploma.domain.api.Resource
import ru.practicum.android.diploma.domain.api.SearchRepository
import ru.practicum.android.diploma.domain.models.main.Vacancy

class SearchRepositoryImpl(
    private val networkClient: NetworkClient,
) : SearchRepository {
    override fun makeRequest(request: VacanciesSearchRequest): Flow<Resource<List<Vacancy>>> = flow {
        val response = networkClient.doRequest(request)
        when (response.resultCode) {
            ResponseCodes.SUCCESS -> {
                with(response as VacanciesSearchResponse){
                    val data = results.vacancyDto.map {
                        it.toVacancy()
                    }
                emit(Resource.Success(data))
                }
            }
            ResponseCodes.ERROR -> emit(Resource.Error(response.resultCode.code))
            ResponseCodes.NO_CONNECTION -> emit(Resource.Error(response.resultCode.code))
        }
    }
}

package ru.practicum.android.diploma.data.vacancylist

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.ResponseCodes
import ru.practicum.android.diploma.data.converters.VacancyConverter.toVacancy
import ru.practicum.android.diploma.data.vacancylist.dto.VacanciesSearchRequest
import ru.practicum.android.diploma.data.vacancylist.dto.VacanciesSearchResponse
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.domain.main.SearchRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class SearchRepositoryImpl(
    private val networkClient: NetworkClient,
) : SearchRepository {

    override fun makeRequest(queryMap: Map<String, String>): Flow<Resource<List<Vacancy>>> = flow {
        val response = networkClient.doRequest(VacanciesSearchRequest(queryMap))

        when (response.resultCode) {
            ResponseCodes.DEFAULT -> emit(Resource.Error(response.resultCode.code))
            ResponseCodes.SUCCESS -> {
                try {
                    val vacancies = (response as VacanciesSearchResponse).items?.map {
                        it.toVacancy()
                    }
                    if (vacancies != null) {
                        emit(Resource.Success(vacancies))
                    } else {
                        emit(Resource.Error(response.resultCode.code))
                    }
                } catch (e: Throwable) {
                    emit(Resource.Error(response.resultCode.code))
                }
            }

            ResponseCodes.ERROR -> emit(Resource.Error(response.resultCode.code))
            ResponseCodes.NO_CONNECTION -> emit(Resource.Error(response.resultCode.code))
            ResponseCodes.SERVER_ERROR -> emit(Resource.Error(response.resultCode.code))
        }
    }
}

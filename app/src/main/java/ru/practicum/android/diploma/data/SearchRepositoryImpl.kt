package ru.practicum.android.diploma.data


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.Resource
import ru.practicum.android.diploma.data.dto.convertors.Convertors
import ru.practicum.android.diploma.data.dto.response.VacancyResponse
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.VacancyRequest
import ru.practicum.android.diploma.domain.api.SearchRepository
import ru.practicum.android.diploma.domain.models.ErrorNetwork
import ru.practicum.android.diploma.domain.models.Vacancy

class SearchRepositoryImpl(
    private val networkClient: NetworkClient
) : SearchRepository {

    override fun search(expression: String) : Flow<Resource<List<Vacancy>>> = flow {
        val response = networkClient.doRequest(dto = VacancyRequest(expression))
        when (response.resultCode) {
            -1 -> {
                emit(Resource.Error(ErrorNetwork.NO_CONNECTIVITY_MESSAGE))
            }

            200 -> {
                emit(Resource.Success((response as VacancyResponse).results.map {
                    Convertors().convertorToVacancy(it)
                }))
            }

            else -> {
                emit(Resource.Error(ErrorNetwork.SERVER_ERROR_MESSAGE))
            }

        }
    }
}

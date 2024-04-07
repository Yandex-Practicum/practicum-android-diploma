package ru.practicum.android.diploma.data.vacancies

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.vacancies.dto.VacanciesSearchDtoResponse
import ru.practicum.android.diploma.data.vacancies.dto.VacanciesSearchRequest
import ru.practicum.android.diploma.data.vacancies.mapper.VacanciesSearchMapper
import ru.practicum.android.diploma.data.vacancies.response.ResponseCodes
import ru.practicum.android.diploma.domain.api.search.VacanciesSearchRepository
import ru.practicum.android.diploma.domain.models.vacacy.VacancyResponse

class VacanciesSearchRepositoryImpl(
    private val networkClient: NetworkClient
) : VacanciesSearchRepository {
    override suspend fun getVacancies(query: String, page: Int): Flow<Pair<VacancyResponse?, String?>> = flow {
        val response =
            networkClient.doRequest(VacanciesSearchRequest(mapOf("text" to query, "page" to page.toString())))

        when (response.resultCode) {
            ResponseCodes.SUCCESS -> {
                emit(Pair(VacanciesSearchMapper.map(response as VacanciesSearchDtoResponse), null))
            }

            ResponseCodes.NO_CONNECTION -> {
                emit(Pair(null, "no connection"))
            }

            ResponseCodes.SERVER_ERROR -> {
                emit(Pair(null, "Server error"))
            }

            else -> emit(Pair(null, "error"))
        }
    }

}

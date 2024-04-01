package ru.practicum.android.diploma.data.vacancies


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.vacancies.dto.VacanciesSearchRequest
import ru.practicum.android.diploma.data.vacancies.dto.VacanciesSearchDtoResponse
import ru.practicum.android.diploma.data.vacancies.mapper.VacanciesSearchMapper
import ru.practicum.android.diploma.data.vacancies.response.ResponseCodes
import ru.practicum.android.diploma.domain.api.search.VacanciesSearchRepository
import ru.practicum.android.diploma.domain.models.vacacy.VacancyResponse

class VacanciesSearchRepositoryImpl(
    private val networkClient: NetworkClient
) : VacanciesSearchRepository {
    override suspend fun getVacancies(queryMap: Map<String, String>): Flow<VacancyResponse> = flow {
        val response = networkClient.doRequest(VacanciesSearchRequest(queryMap))

        when (response.resultCode) {
            ResponseCodes.SUCCESS -> {
                if (response is VacanciesSearchDtoResponse) {
                    emit(VacanciesSearchMapper.map(response))
                } else {
                    throw Exception("Result is not valid model")
                }
            }

            else -> {
                throw Exception("Network error")
            }
        }
    }

}

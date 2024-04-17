package ru.practicum.android.diploma.data.vacancies

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.vacancies.details.DetailRequest
import ru.practicum.android.diploma.data.vacancies.details.VacancyDetailDtoResponse
import ru.practicum.android.diploma.data.vacancies.mapper.VacancyDetailsMapper
import ru.practicum.android.diploma.data.vacancies.response.ResponseCodes
import ru.practicum.android.diploma.domain.api.details.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.models.VacancyDetails

class VacancyDetailsRepositoryImpl(
    private val networkClient: NetworkClient
) : VacancyDetailsRepository {
    override suspend fun getVacancyDetails(id: String): Flow<VacancyDetails> = flow {
        val response = networkClient.doRequest(DetailRequest(id))

        when (response.resultCode) {
            ResponseCodes.SUCCESS -> {
                if (response is VacancyDetailDtoResponse) {
                    emit(VacancyDetailsMapper.map(response))
                } else {
                    throw VacancyDetailsException("Result is not valid model")
                }
            }
            else -> {
                throw VacancyDetailsException("Network error")
            }
        }

    }
}

class VacancyDetailsException(message: String) : Exception(message)

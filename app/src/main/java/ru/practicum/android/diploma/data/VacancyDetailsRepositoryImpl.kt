package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.dto.VacancyDetailDto
import ru.practicum.android.diploma.data.network.VacancyDetailsRequest
import ru.practicum.android.diploma.domain.api.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.models.GetVacancyDetailsResponse

class VacancyDetailsRepositoryImpl(
    private val networkClient: NetworkClient,
) : VacancyDetailsRepository {
    override suspend fun getVacancyDetails(id: String): GetVacancyDetailsResponse {
        val response = networkClient.doRequest(
            VacancyDetailsRequest(id = id),
        )
        val data = response.data as? VacancyDetailDto
        return when {
            data == null -> GetVacancyDetailsResponse.Error
            response.resultCode != HTTP_OK -> {
                when (response.resultCode) {
                    HTTP_NOT_FOUND -> GetVacancyDetailsResponse.NotFound
                    HTTP_SERVER_ERROR -> GetVacancyDetailsResponse.ServerError
                    else -> GetVacancyDetailsResponse.Error
                }
            }
            else -> {
                val domainResult = data.toDomain()
                GetVacancyDetailsResponse.Success(domainResult)
            }
        }
    }

    private companion object {
        const val HTTP_OK = 200

        const val HTTP_NOT_FOUND = 404

        const val HTTP_SERVER_ERROR = 500
    }
}

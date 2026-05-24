package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.dto.VacancyDetailDto
import ru.practicum.android.diploma.data.network.VacancyDetailsRequest
import ru.practicum.android.diploma.domain.api.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.models.detail.GetVacancyDetailsResponse

class VacancyDetailsRepositoryImpl(
    private val networkClient: NetworkClient,
): VacancyDetailsRepository {
    override suspend fun getVacancyDetails(id: String): GetVacancyDetailsResponse {
        val response = networkClient.doRequest(
            VacancyDetailsRequest(id = id),
        )
        val data = response.data as? VacancyDetailDto
        return when {
            response.resultCode != HTTP_OK || data == null -> GetVacancyDetailsResponse.Error
            else -> {
                val domainResult = data.toDomain()
                return GetVacancyDetailsResponse.Success(domainResult)
            }
        }
    }

    private companion object {
        const val HTTP_OK = 200
    }
}

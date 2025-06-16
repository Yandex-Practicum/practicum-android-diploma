package ru.practicum.android.diploma.data.vacancy

import ru.practicum.android.diploma.data.VacancyDetailRequest
import ru.practicum.android.diploma.data.network.ApiResponse
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.vacancy.models.VacancyDetailsDto
import ru.practicum.android.diploma.domain.vacancy.models.VacancyDetails
import ru.practicum.android.diploma.util.HTTP_200_OK
import ru.practicum.android.diploma.util.HTTP_400_BAD_REQUEST
import ru.practicum.android.diploma.util.HTTP_500_INTERNAL_SERVER_ERROR
import ru.practicum.android.diploma.util.HTTP_NO_CONNECTION

class VacancyDetailsNetworkDataSource(
    private val networkClient: NetworkClient,
) {
    suspend fun getVacancyDetails(id: String): ApiResponse<VacancyDetails> {
        val request = VacancyDetailRequest(id)
        val response = networkClient.doRequest(request)
        return when (response.resultCode) {
            HTTP_NO_CONNECTION -> ApiResponse.Error(HTTP_NO_CONNECTION)
            HTTP_200_OK -> {
                with(response as VacancyDetailsDto) {
                    val data = formatDetails(response)
                    ApiResponse.Success(data, 0, 0)
                }
            }

            HTTP_400_BAD_REQUEST -> ApiResponse.Error(HTTP_400_BAD_REQUEST)
            else -> ApiResponse.Error(HTTP_500_INTERNAL_SERVER_ERROR)
        }
    }

    private fun formatDetails(dto: VacancyDetailsDto): VacancyDetails {
        return VacancyDetails(
            id = dto.id,
            title = dto.name,
            employerName = dto.employer?.name ?: "",
            logoUrl = dto.employer?.logoUrls?.size90,
            salaryFrom = dto.salary?.from,
            salaryTo = dto.salary?.to,
            salaryCurrency = dto.salary?.currency,
            experience = dto.experience?.name,
            employment = dto.employment?.name,
            schedule = dto.schedule?.name,
            descriptionHtml = dto.description ?: "",
            keySkills = dto.keySkills?.mapNotNull { it.name } ?: emptyList(),
            address = dto.address?.raw,
            areaName = dto.area?.name
        )
    }
}

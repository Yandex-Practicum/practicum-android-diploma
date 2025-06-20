package ru.practicum.android.diploma.data.vacancy

import ru.practicum.android.diploma.data.VacancyDetailRequest
import ru.practicum.android.diploma.data.db.dao.VacanciesDao
import ru.practicum.android.diploma.data.network.ApiResponse
import ru.practicum.android.diploma.data.network.NetworkClientInterface
import ru.practicum.android.diploma.data.vacancy.models.VacancyDetailsDto
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.util.HTTP_200_OK
import ru.practicum.android.diploma.util.HTTP_400_BAD_REQUEST
import ru.practicum.android.diploma.util.HTTP_500_INTERNAL_SERVER_ERROR
import ru.practicum.android.diploma.util.HTTP_NO_CONNECTION

class VacancyDetailsNetworkDataSource(
    private val networkClient: NetworkClientInterface,
    private val appDataDao: VacanciesDao
) {
    suspend fun getVacancyDetails(id: String): ApiResponse<VacancyDetails> {
        val request = VacancyDetailRequest(id)
        val response = networkClient.doRequest(request)
        return when (response.resultCode) {
            HTTP_NO_CONNECTION -> ApiResponse.Error(HTTP_NO_CONNECTION)
            HTTP_200_OK -> {
                with(response as VacancyDetailsDto) {
                    val data = formatDetails(response)
                    ApiResponse.Success(data, 0, 0, 0)
                }
            }

            HTTP_400_BAD_REQUEST -> ApiResponse.Error(HTTP_400_BAD_REQUEST)
            else -> ApiResponse.Error(HTTP_500_INTERNAL_SERVER_ERROR)
        }
    }

    private suspend fun formatDetails(dto: VacancyDetailsDto): VacancyDetails {
        val isFavorite = appDataDao.getFavoriteVacancieById(dto.id) != null
        return VacancyDetails(
            vacancy = Vacancy(
                id = dto.id,
                name = dto.name,
                areaName = dto.area.name,
                employerName = dto.employer?.name ?: "",
                employerUrls = dto.employer?.logoUrls?.size90,
                salaryFrom = dto.salary?.from,
                salaryTo = dto.salary?.to,
                salaryCurr = dto.salary?.currency ?: "RUR",
            ),
            experience = dto.experience.name,
            employmentForm = dto.employmentForm?.name ?: "",
            description = dto.description,
            keySkills = dto.keySkills.map { it.name },
            schedule = dto.schedule?.map { it.name } ?: emptyList(),
            professionalRoles = dto.professionalRoles.map { it.name },
            address = dto.address?.city ?: "",
            isFavorite = isFavorite,
            employment = dto.employment?.name,
            url = dto.alternateUrl
        )
    }
}

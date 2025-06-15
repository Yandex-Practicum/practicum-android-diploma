package ru.practicum.android.diploma.data.vacancy

import ru.practicum.android.diploma.data.network.ApiResponse
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.vacancy.models.VacancyDetailsDto
import ru.practicum.android.diploma.domain.vacancy.models.VacancyDetails

class VacancyDetailsNetworkDataSource(
    private val hhApi: HhApi,
    private val networkClient: NetworkClient,
) {
    suspend fun getVacancyDetails(id: String): ApiResponse<VacancyDetails> {
        return networkClient.execute {
            val dto = hhApi.getVacancyDetails(id)
            formatDetails(dto)
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
            keySkills = dto.keySkills?.mapNotNull { it.name } ?: emptyList()
        )
    }
}

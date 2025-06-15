package ru.practicum.android.diploma.data.vacancy

import ru.practicum.android.diploma.data.network.ApiResponse
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.vacancy.models.VacancyDetailsDto
import ru.practicum.android.diploma.data.vacancy.models.VacancyDto
import ru.practicum.android.diploma.domain.vacancy.models.Vacancy
import ru.practicum.android.diploma.domain.vacancy.models.VacancyDetails

class SearchVacanciesNetworkDataSource(
    private val hhApi: HhApi,
    private val networkClient: NetworkClient,
) {
    suspend fun getSearchResults(text: String): ApiResponse<List<Vacancy>> {
        return networkClient.execute {
            val result = hhApi.searchVacancies(text)
            result.items.map { formatVacancy(it) }
        }
    }

    private fun formatVacancy(data: VacancyDto): Vacancy {
        return Vacancy(
            id = data.id,
            title = data.name,
            employerTitle = data.employer.name,
            logoUrl = data.employer.logoUrls?.size240,
            salaryRange = data.salaryRange?.let {
                Vacancy.VacancySalaryRange(
                    currency = data.salaryRange.currency,
                    from = data.salaryRange.from,
                    to = data.salaryRange.to,
                )
            }
        )
    }

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

package ru.practicum.android.diploma.data.vacancy

import ru.practicum.android.diploma.data.network.ApiResponse
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.vacancy.models.VacancyDto
import ru.practicum.android.diploma.domain.vacancy.models.Vacancy

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
}

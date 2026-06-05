package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.dto.VacanciesRequest
import ru.practicum.android.diploma.data.dto.VacanciesResponseDto
import ru.practicum.android.diploma.domain.api.VacanciesRepository
import ru.practicum.android.diploma.domain.models.FilterParameters
import ru.practicum.android.diploma.domain.models.SearchVacanciesOutcome

class VacanciesRepositoryImpl(
    private val networkClient: NetworkClient,
) : VacanciesRepository {

    override suspend fun searchVacancies(
        searchText: String,
        page: Int,
        filterParameters: FilterParameters
    ): SearchVacanciesOutcome {
        val response = networkClient.doRequest(
            VacanciesRequest(
                searchText = searchText,
                page = page,
                area = filterParameters.area,
                industry = filterParameters.industryId,
                salary = filterParameters.salary,
                onlyWithSalary = filterParameters.hideWithoutSalary
            ),
        )
        val data = response.data as? VacanciesResponseDto
        return when {
            response.resultCode != HTTP_OK || data == null -> SearchVacanciesOutcome.Error
            else -> {
                val domainResult = data.toDomain()
                if (domainResult.vacancies.isEmpty()) {
                    SearchVacanciesOutcome.Empty
                } else {
                    SearchVacanciesOutcome.Success(domainResult)
                }
            }
        }
    }

    private companion object {
        const val HTTP_OK = 200
    }
}

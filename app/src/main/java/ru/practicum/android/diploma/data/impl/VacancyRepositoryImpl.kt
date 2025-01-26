package ru.practicum.android.diploma.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.dto.Request
import ru.practicum.android.diploma.data.dto.VacanciesResponse
import ru.practicum.android.diploma.data.dto.VacancyDto
import ru.practicum.android.diploma.data.dto.VacancyResponse
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.api.VacancyRepository
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Page
import ru.practicum.android.diploma.domain.models.Resource
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyRepositoryImpl(
    private val networkClient: NetworkClient
) : VacancyRepository {
    companion object {
        private const val SuccessfulRequest = 200
    }

    override fun getVacancies(options: Map<String, String>): Flow<Resource<Page>> = flow {
        val request = Request.VacanciesRequest(options)
        val response = networkClient.doRequest(request) as VacanciesResponse
        val result = if (response.resultCode == SuccessfulRequest) {
            Resource.Success(
                Page(
                    response.items.map { convertFromVacancyDto(it) },
                    response.page,
                    response.pages
                )
            )
        } else {
            Resource.Error("${response.resultCode}")
        }
        emit(result)
    }

    override fun getVacancy(vacancyId: String): Flow<Resource<Vacancy>> = flow {
        val request = Request.VacancyRequest(vacancyId)
        val response = networkClient.doRequest(request) as VacancyResponse
        val result = if (response.resultCode == SuccessfulRequest) {
            Resource.Success(convertFromVacancyDto(response.vacancy))
        } else {
            Resource.Error("${response.resultCode}")
        }
        emit(result)
    }

    private fun convertFromVacancyDto(vacancy: VacancyDto): Vacancy {
        val salary = if (vacancy.salary != null) {
            Salary(
                vacancy.salary.currency,
                vacancy.salary.from,
                vacancy.salary.to
            )
        } else {
            null
        }
        return Vacancy(
            vacancy.id,
            vacancy.name,
            vacancy.employer.logoUrls?.url90,
            vacancy.area.name,
            salary,
            vacancy.employer.name,
            vacancy.employment?.name,
            vacancy.experience?.name,
            vacancy.description,
            vacancy.alternateUrl
        )
    }
}

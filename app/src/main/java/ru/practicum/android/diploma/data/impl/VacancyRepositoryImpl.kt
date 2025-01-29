package ru.practicum.android.diploma.data.impl

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.dto.Request
import ru.practicum.android.diploma.data.dto.VacanciesResponse
import ru.practicum.android.diploma.data.dto.VacancyDto
import ru.practicum.android.diploma.data.dto.VacancyResponse
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.api.VacancyRepository
import ru.practicum.android.diploma.domain.models.Page
import ru.practicum.android.diploma.domain.models.Resource
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyRepositoryImpl(
    private val networkClient: NetworkClient
) : VacancyRepository {
    companion object {
        private const val SUCCESSFULREQUEST = 200
    }

    override fun getVacancies(options: Map<String, String>): Flow<Resource<Page>> = flow {
        val request = Request.VacanciesRequest(options)
        val response = networkClient.doRequest(request)
        val result = if (response.resultCode == SUCCESSFULREQUEST) {
            with(response as  VacanciesResponse){
                Resource.Success(
                    Page(
                        items.map { convertFromVacancyDto(it) },
                        page,
                        pages
                    )
                )
            }

        } else {
            Log.d("TAG", "getVacancies: ")
            Resource.Error("${response.resultCode}")
        }
        emit(result)
    }

    override fun getVacancy(vacancyId: String): Flow<Resource<Vacancy>> = flow {
        val request = Request.VacancyRequest(vacancyId)
        val response = networkClient.doRequest(request) as VacancyResponse
        val result = if (response.resultCode == SUCCESSFULREQUEST) {
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

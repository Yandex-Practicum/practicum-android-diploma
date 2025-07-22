package ru.practicum.android.diploma.data.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.dto.VacanciesRequest
import ru.practicum.android.diploma.data.dto.VacanciesResponse
import ru.practicum.android.diploma.domain.models.ProfessionalRole
import ru.practicum.android.diploma.domain.models.Vacancy
import kotlin.String

class VacanciesRepositoryImpl() : VacanciesRepository {

    val networkClient = RetrofitNetworkClient()

    override fun searchVacancies(
        page: Int
    ): Flow<List<Vacancy>> = flow {
        val networkClientResponse = networkClient.doRequest(VacanciesRequest(page))

        when (networkClientResponse.resultCode) {
            200 -> {
                emit(
                    (networkClientResponse as VacanciesResponse).vacanciesList.map {
                        with(it) {
                            Vacancy(
                                name = name,
                                area = area?.name ?: "",
                                employer = employer?.name ?: "",
                                salaryFrom = salary?.from ?: 0,
                                salaryTo = salary?.to ?: 0,
                                schedule = schedule.name,
                                experience = experience?.name ?: "",
                                employerLogo = employer?.logos?.size90?:"",
                                snippetTitle = snippet.requirement?:"",
                                snippetDescription = snippet.requirement?:"",
                                professionalRoles = professionalRoles?.map {
                                    ProfessionalRole(it.id, it.name)
                                } ?: listOf(),
                                employment = employment.name,
                            )
                        }
                    }
                )
            }

            400 -> {}
            else -> {}
        }
    }
}

package ru.practicum.android.diploma.vacancy.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.search.data.dto.RESULT_CODE_SUCCESS
import ru.practicum.android.diploma.search.data.dto.toStr
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.vacancy.data.dto.VacancyRequest
import ru.practicum.android.diploma.vacancy.data.dto.VacancyResponse
import ru.practicum.android.diploma.vacancy.domain.api.VacancyRepository
import ru.practicum.android.diploma.vacancy.domain.models.VacancyFull

class VacancyRepositoryImpl(private val networkClient: NetworkClient) : VacancyRepository {

    override fun getVacancy(id: Int): Flow<VacancyFull> = flow {
        val response = networkClient.doRequest(VacancyRequest(id = id))
        when (response.resultCode) {
            RESULT_CODE_SUCCESS -> {
                with(response as VacancyResponse) {
                    val vacancyFull = VacancyFull(
                        id = response.id,
                        name = response.name,
                        company = response.employer.name,
                        salary = response.salary?.toStr() ?: "salary: null",
                        area = response.area.name,
                        alternateUrl = response.alternateUrl,
                        icon = response.employer.logoUrls?.logo240 ?: "",
                        employment = response.employment?.name ?: "employment null",
                        experience = response.experience?.name ?: "experience null",
                        schedule = response.schedule.name,
                        description = response.description,
                    )
                    emit(vacancyFull)
                }
            }

            else -> emit(VacancyFull())
        }
    }
}

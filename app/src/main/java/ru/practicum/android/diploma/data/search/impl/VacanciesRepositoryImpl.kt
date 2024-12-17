package ru.practicum.android.diploma.data.search.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.dto.VacancySearchRequest
import ru.practicum.android.diploma.data.dto.VacancySearchResponse
import ru.practicum.android.diploma.data.dto.model.SalaryDto
import ru.practicum.android.diploma.data.dto.network.RetrofitNetworkClient
import ru.practicum.android.diploma.data.search.VacanciesRepository
import ru.practicum.android.diploma.domain.NetworkClient
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.search.models.SearchParams

class VacanciesRepositoryImpl(
    private val networkClient: NetworkClient
) : VacanciesRepository {

    override fun getVacancies(searchParams: SearchParams): Flow<List<Vacancy>> {
        return flow {
            val response = networkClient.doRequest(VacancySearchRequest(searchParams))
            when(response.resultCode) {
                RetrofitNetworkClient.HTTP_OK_CODE -> {
                    with(response as VacancySearchResponse) {
                        val listOfFoundedVacancies = items.map {
                            Vacancy(
                                it.id,
                                it.name,
                                it.area.name,
                                getCorrectFormOfSalaryText(it.salary),
                                it.employer.name,
                                it.employer.logoUrls?.original
                            )
                        }
                        emit(listOfFoundedVacancies)
                    }
                }
                else -> {
                    emit(
                        listOf(
                            Vacancy(
                                "-1",
                                "",
                                "",
                                null,
                                "",
                                null
                            )
                        )
                    )
                }
            }
        }
    }

    private fun getCorrectFormOfSalaryText(salary: SalaryDto?): String? {
        return if (salary == null) {
            null
        } else {
            if (salary.from == salary.to) {
                String.format("%D %S", salary.to, salary.currency)
            } else if (salary.to == null) {
                String.format("от %D %S", salary.from, salary.currency)
            } else {
                String.format("от %D до %D %S", salary.from, salary.to, salary.currency)
            }
        }
    }

}

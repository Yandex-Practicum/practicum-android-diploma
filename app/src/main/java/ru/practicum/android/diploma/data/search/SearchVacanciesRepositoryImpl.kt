package ru.practicum.android.diploma.data.search

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.practicum.android.diploma.data.dto.VacancyRequest
import ru.practicum.android.diploma.data.dto.VacancyResponse
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.NetworkCodes
import ru.practicum.android.diploma.domain.api.SearchVacanciesRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancySearchResult

class SearchVacanciesRepositoryImpl(
    private val networkClient: NetworkClient
) : SearchVacanciesRepository {

    override fun searchVacancies(expression: String): Flow<VacancySearchResult> = flow {
        val response = networkClient.doRequest(VacancyRequest(expression))

        when (response.resultCode) {

            NetworkCodes.SUCCESS_CODE -> {
                val vacanciesResponse = response as VacancyResponse
                val vacancies: List<Vacancy> =
                    VacancyDtoMapper.mapList(vacanciesResponse.vacancies)

                emit(
                    VacancySearchResult(
                        totalFound = vacanciesResponse.found,
                        totalPages = vacanciesResponse.pages,
                        vacancies = vacancies,
                        errorCode = NetworkCodes.SUCCESS_CODE
                    )
                )
            }

            else -> {
                emit(
                    VacancySearchResult(
                        totalFound = 0,
                        totalPages = 0,
                        vacancies = emptyList(),
                        errorCode = response.resultCode
                    )
                )
            }
        }
    }.flowOn(Dispatchers.IO)
}

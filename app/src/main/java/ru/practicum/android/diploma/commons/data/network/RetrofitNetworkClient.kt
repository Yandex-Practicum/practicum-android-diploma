package ru.practicum.android.diploma.commons.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.commons.data.dto.Response
import ru.practicum.android.diploma.commons.data.dto.vacancies_search.VacanciesSearchByNameRequest
import ru.practicum.android.diploma.commons.data.dto.vacancies_search.VacanciesSearchSimilarRequest
import ru.practicum.android.diploma.commons.data.dto.vacancy_detailed.VacancyDetailedRequest

class RetrofitNetworkClient(
    private val headHunterService: HeadHunterServiceApi
) : NetworkClient {

    override suspend fun doRequest(
        dto: Any
    ): Response {
        return withContext(Dispatchers.IO) {
            try {
                when (dto) {
                    is VacanciesSearchByNameRequest -> {
                        headHunterService.searchVacancies(dto.name, dto.page, dto.amount).apply {
                            responseCode = 200
                        }
                    }

                    is VacanciesSearchSimilarRequest -> {
                        headHunterService.searchSimilarVacancies(dto.id, dto.page, dto.amount).apply {
                            responseCode = 200
                        }
                    }

                    is VacancyDetailedRequest -> {
                        headHunterService.searchConcreteVacancy(dto.id).apply {
                            responseCode = 200
                        }
                    }

                    else -> {
                        Response().apply {
                            responseCode = 400
                        }
                    }
                }
            } catch (e: Throwable) {
                Response().apply {
                    responseCode = 503
                }
            }
        }
    }

}

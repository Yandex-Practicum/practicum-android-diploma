package ru.practicum.android.diploma.commons.data.network

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.commons.data.Constant.BAD_REQUEST_RESULT_CODE
import ru.practicum.android.diploma.commons.data.Constant.NO_CONNECTIVITY_MESSAGE
import ru.practicum.android.diploma.commons.data.Constant.SUCCESS_RESULT_CODE
import ru.practicum.android.diploma.commons.data.dto.Response
import ru.practicum.android.diploma.commons.data.dto.detailed.VacancyDetailedRequest
import ru.practicum.android.diploma.commons.data.dto.search.VacanciesSearchByNameRequest
import ru.practicum.android.diploma.util.isConnected

class RetrofitNetworkClient(
    private val headHunterService: HeadHunterServiceApi,
    private val context: Context
) : NetworkClient {

    override suspend fun doRequest(
        dto: Any
    ): Response {
        return withContext(Dispatchers.IO) {
            try {
                when (dto) {
                    is VacanciesSearchByNameRequest -> {
                        headHunterService.searchVacancies(dto.name, dto.page, dto.amount).apply {
                            responseCode = SUCCESS_RESULT_CODE
                        }
                    }

                    is VacancyDetailedRequest -> {
                        headHunterService.searchConcreteVacancy(dto.id).apply {
                            responseCode = SUCCESS_RESULT_CODE
                        }
                    }

                    else -> {
                        Response().apply {
                            responseCode = BAD_REQUEST_RESULT_CODE
                        }
                    }
                }
            } catch (exception: HttpException) {
                Response().apply { responseCode = exception.code() }

            }
        }
    }

    // с doRequest через When красивее было, что ревьюру не понравилось?
    override suspend fun getDetailVacancy(dto: VacancyDetailedRequest): Response {
        if (isConnected(context)) {
            return Response().apply { responseCode = NO_CONNECTIVITY_MESSAGE }
        }
        return withContext(Dispatchers.IO) {
            try {
                headHunterService.searchConcreteVacancy(dto.id).apply {
                    responseCode = SUCCESS_RESULT_CODE
                }
            } catch (exception: HttpException) {
                Response().apply { responseCode = exception.code() }
            }
        }
    }
}

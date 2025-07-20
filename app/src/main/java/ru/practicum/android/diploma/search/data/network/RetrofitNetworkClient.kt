package ru.practicum.android.diploma.search.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.search.data.model.Response
import ru.practicum.android.diploma.search.data.model.VacancyRequest
import ru.practicum.android.diploma.util.InternetConnectionChecker

class RetrofitNetworkClient(
    private val networkService: RetrofitClient,
    private val internetConnectionChecker: InternetConnectionChecker,
) : NetworkClient {

    override suspend fun getVacancies(vacancyRequest: VacancyRequest): Response {
        return try {
            withContext(Dispatchers.IO) {
                if (internetConnectionChecker.isInternetAvailable()) {
                    val response = networkService.hhApi.getVacancyByName(
                        filters = vacancyRequest.toQueryMap()
                    )

                    when {
                        response.items.isEmpty() -> {
                            response.resultCode = EMPTY
                            response
                        }

                        else -> {
                            response.resultCode = OK_RESPONSE
                            response
                        }
                    }

                } else {
                    Response().apply {
                        resultCode = NO_INTERNET
                    }
                }
            }
        } catch (e: HttpException) {
            Response().apply {
                resultCode = e.code()
            }
        }
    }

    companion object {
        const val OK_RESPONSE = 200
        const val NO_INTERNET = -1
        const val EMPTY = 404
    }

}

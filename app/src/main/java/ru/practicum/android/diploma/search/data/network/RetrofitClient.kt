package ru.practicum.android.diploma.search.data.network

import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.search.data.network.dto.response.CountriesResponse
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class RetrofitClient @Inject constructor(
    private val hhApiService: HhApiService,
    private val internetController: InternetController,
    private val logger: Logger,
) : NetworkClient {

    override suspend fun doRequest(any: Any): Response {
        logger.log(thisName, "doRequest: ${any.thisName} ")

        if (any !is VacancyRequest || !internetController.isInternetAvailable()) {
            return Response().apply { resultCode = -1 }

        } else {
            val query = when (any) {
                is VacancyRequest.FullInfoRequest -> {
                    any.id
                }

                is VacancyRequest.SearchVacanciesRequest -> {
                    any.query
                }
            }

            val result = try {
                hhApiService.search(query)
            } catch (e: Exception) {
                null
            }

            return result
                ?.body()
                ?.apply { resultCode = result.code() } ?: Response().apply { resultCode = 400 }
        }
    }

    override suspend fun doCountryRequest(): Response {
        if (!internetController.isInternetAvailable()) {
            return Response().apply { resultCode = -1 }
        }
        val response =
            hhApiService.getCountries()
        val result = CountriesResponse(response.body() ?: emptyList())
        result.resultCode = response.code()
        return result
    }
}


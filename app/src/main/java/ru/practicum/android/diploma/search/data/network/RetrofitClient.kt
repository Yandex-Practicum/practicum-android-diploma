package ru.practicum.android.diploma.search.data.network

import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.search.data.network.dto.response.CountriesCodeResponse
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class RetrofitClient @Inject constructor(
    private val hhApiService: HhApiService,
    private val internetController: InternetController,
    private val logger: Logger,
) : NetworkClient {

    override suspend fun doRequest(request: Any): CodeResponse {
        logger.log(thisName, "doRequest: ${request.thisName} ")

        if (!internetController.isInternetAvailable()) {
            return CodeResponse().apply { resultCode = -1 }
        }

        val query = when (request) {
            is VacancyRequest.FullInfoRequest -> {
                logger.log(thisName, "is FullInfoRequest -> ${request.id}")
                request.id
            }
            is VacancyRequest.SearchVacanciesRequest -> {
                logger.log(thisName, "is SearchVacanciesRequest -> ${request.query}")
                request.query
            }
            //is Olegs search -> {request.query}
            else -> { return CodeResponse().apply { resultCode = 400 } }
        }


        val result = try {
            logger.log(thisName, "hhApiService.search($query)")
            hhApiService.search(query)
        } catch (e:Exception) {
            logger.log(thisName, "hhApiService.search(query) return null")
            null
        }

        return result?.body()
            ?.apply {
                logger.log(thisName, "resultCode = ${result.code()}")
                resultCode = result.code()
            }
            ?: CodeResponse().apply {
                logger.log(thisName, "resultCode = ${result?.code() ?: 400}")
                resultCode = result?.code() ?: 400
            }

}

    override suspend fun doCountryRequest(): CodeResponse {
        logger.log(thisName, "doCountryRequest(): Response")
        if (!internetController.isInternetAvailable()) {
            return CodeResponse().apply { resultCode = -1 }
        }
        val response = hhApiService.getCountries()
        val result = CountriesCodeResponse(response?.body() ?: emptyList())
        result.resultCode = response?.code()!!
        return result
    }
}


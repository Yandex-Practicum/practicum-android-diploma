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

        logger.log(thisName, "21++++++++")
        val query = when (request) {
            is VacancyRequest.FullInfoRequest -> { request.id }
            is VacancyRequest.SearchVacanciesRequest -> {request.query}
            //is Olegs search -> {request.query}
            else -> { return CodeResponse().apply { resultCode = 400 } }
        }


        val result = try {
            logger.log(thisName, "start search")
            hhApiService.search(query)
        } catch (e:Exception) {
            logger.log(thisName, "null")
            null
        }
        logger.log(thisName, "val result = ${result?.body()}")
        return result?.body()?.apply { resultCode = result.code() }
            ?: CodeResponse().apply { resultCode = result?.code() ?: 400 }

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


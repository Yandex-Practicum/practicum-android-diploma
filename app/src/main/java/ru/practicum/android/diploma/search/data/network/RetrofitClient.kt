package ru.practicum.android.diploma.search.data.network

import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.data.model.Filter
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

        val result = when (request) {
            is Vacancy.FullInfoRequest -> {
                logger.log(thisName, "is Vacancy.FullInfoRequest -> ${request.id}")
                hhApiService.searchDetails(request.id)
            }

            is Vacancy.SimilarVacanciesRequest -> {
                logger.log(thisName, "is Vacancy.SimilarVacanciesRequest -> ${request.id}")
                hhApiService.getSimilarVacancies(request.id)
            }

            is Vacancy.SearchRequest -> {
                logger.log(thisName, "is Vacancy.SearchRequest -> ${request.query}")
                hhApiService.search(request.query)
            }

            is Filter.CountryRequest -> {
                logger.log(thisName, "is Filter.CountryRequest -> hhApiService.getCountries()")
                val response = hhApiService.getCountries()
                val result = CountriesCodeResponse(response.body() ?: emptyList())
                result.resultCode = response.code()
                return result
            }
            is Filter.RegionRequest -> {
               logger.log(thisName, "is Filter.RegionRequest -> hhApiService.getRegionInfo()")
                hhApiService.getRegionInfo(request.query).also {                 logger.log(thisName, "is Filter.RegionRequest -> hhApiService.getRegionInfo(${request.query})") }
            }
            else -> {
                logger.log(thisName, "else -> resultCode = 400")
                return CodeResponse().apply { resultCode = 400 }
            }
        }

        return result.body()?.apply {
            logger.log("RetrofitClient", "resultCode = ${result.code()}")
          resultCode = result.code()
        } as CodeResponse

    }

}


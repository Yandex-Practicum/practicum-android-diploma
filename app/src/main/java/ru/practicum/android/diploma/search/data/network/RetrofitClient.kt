package ru.practicum.android.diploma.search.data.network

import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.data.model.Filter
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
                hhApiService.search(request.id)
            }
            is Vacancy.SearchRequest -> {
                logger.log(thisName, "is Vacancy.SearchRequest -> ${request.query}")
                hhApiService.search(request.query)
            }
            is Filter.CountryRequest -> {
                logger.log(thisName, "is Filter.CountryRequest -> hhApiService.getCountries()")
                hhApiService.getCountries()
            }
//            is Filter.CityRequest -> {
//                logger.log(thisName, "is Filter.CityRequest -> hhApiService.getCities()")
//                hhApiService.getCities()
//            }
            else -> {
                logger.log(thisName, "else -> resultCode = 400")
                return CodeResponse().apply { resultCode = 400 }
            }
        }

        return result.body()?.apply {
            logger.log("RetrofitClient", "resultCode = ${result.code()}")
            (this as CodeResponse).resultCode = result.code()
        } as CodeResponse

    }

}


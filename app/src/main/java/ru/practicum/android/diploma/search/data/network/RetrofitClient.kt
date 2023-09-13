/*package ru.practicum.android.diploma.search.data.network

import ru.practicum.android.diploma.Logger
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

}*/


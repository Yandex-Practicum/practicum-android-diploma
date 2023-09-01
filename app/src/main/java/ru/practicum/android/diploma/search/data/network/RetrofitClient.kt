package ru.practicum.android.diploma.search.data.network

import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.search.data.network.converter.VacancyModelConverter
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class RetrofitClient @Inject constructor(
    private val hhApiService: HhApiService,
    private val internetController: InternetController,
    private val logger: Logger,
) : NetworkClient {
    
    override suspend fun doRequest(any: Any): Response {
        logger.log(thisName, "doRequest -> ${any::class} ")
        
        if (any !is VacancyRequest || !internetController.isInternetAvailable()) {
            return Response().apply { resultCode = -1 }
            
        } else {
            val query = when (any) {
                is VacancyRequest.FullInfoRequest -> { any.id }
                is VacancyRequest.SearchVacanciesRequest -> { any.query }
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
}

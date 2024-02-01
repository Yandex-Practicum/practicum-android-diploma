package ru.practicum.android.diploma.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class RetrofitNetworkClient(
    private val service: HhApi,
) : NetworkClient {
    companion object {
        private const val SUCCESS_RESULT_CODE = 200
        private const val BAD_REQUEST_RESULT_CODE = 400
        private const val SERVER_ERROR_RESULT_CODE = 500
        const val HH_BASE_URL = "https://api.hh.ru/"
    }
    override suspend fun doRequest(dto: Any): Response = withContext(Dispatchers.IO) {
        try {
            val result = if (dto is VacancyRequest) {
                val responseSearch = service.jobSearch(dto.expression)
                val responseCountry = service.filterCountry()
                val responseRegion = service.filterRegion(dto.expression)
                val responseIndustry = service.filterIndustry()

                setSuccessResultCode(responseSearch, responseCountry, responseRegion, responseIndustry)

                Response().apply { resultCode = SUCCESS_RESULT_CODE }
            } else {
                Response().apply { resultCode = BAD_REQUEST_RESULT_CODE }
            }

            result
        } catch (e: IOException) {
            Response().apply {
                resultCode = SERVER_ERROR_RESULT_CODE
                errorMessage = "Network error: ${e.message}"
            }
        } catch (e: HttpException) {
            Response().apply {
                resultCode = SERVER_ERROR_RESULT_CODE
                errorMessage = "HTTP error: ${e.code()} ${e.message()}"
            }
        }
    }
    private fun setSuccessResultCode(vararg responses: Response) {
        responses.forEach { it.resultCode = SUCCESS_RESULT_CODE }
    }
}

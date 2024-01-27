package ru.practicum.android.diploma.data.network

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RetrofitNetworkClient(
    private val service: HhApi,
) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response = withContext(Dispatchers.IO) {
        try {
            if (dto !is JobSearchRequest) {
                return@withContext Response().apply { resultCode = 400 }
            }

            val responseSearch = service.jobSearch(dto.expression)
            val responseCountry = service.filterCountry()
            val responseRegion = service.filterRegion(dto.expression)
            val responseIndustry = service.filterIndustry()

            setSuccessResultCode(responseSearch, responseCountry, responseRegion, responseIndustry)

            Response().apply { resultCode = 200 }

        } catch (e: Throwable) {
            Response().apply { resultCode = 500 }
        }
    }

    private fun setSuccessResultCode(vararg responses: Response) {
        responses.forEach { it.resultCode = 200 }
    }
}

package ru.practicum.android.diploma.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.IRetrofitApiClient
import ru.practicum.android.diploma.data.dto.ApiResponse
import ru.practicum.android.diploma.data.network.dto.GetAreasRequest
import ru.practicum.android.diploma.data.network.dto.GetAreasResponse
import ru.practicum.android.diploma.data.network.dto.GetIndustriesRequest
import ru.practicum.android.diploma.data.network.dto.GetIndustriesResponse
import ru.practicum.android.diploma.data.network.dto.GetVacancyDetailsRequest
import ru.practicum.android.diploma.data.network.dto.SearchVacanciesRequest

class RetrofitApiClient(
    private val api: IApiService
) : IRetrofitApiClient {

    override suspend fun searchVacancies(req: SearchVacanciesRequest): ApiResponse {
        return try {
            withContext(Dispatchers.IO) {
                api.searchVacancies(req.toQueryParams()).apply { resultCode = 200 }
            }
        } catch (e: Throwable) {
            ApiResponse().apply { resultCode = 500 }
        }
    }

    override suspend fun getVacancyDetails(req: GetVacancyDetailsRequest): ApiResponse {
        return try {
            withContext(Dispatchers.IO) {
                api.getVacancyDetails(id = req.vacancyId).apply { resultCode = 200 }
            }
        } catch (e: Throwable) {
            ApiResponse().apply { resultCode = 500 }
        }
    }

    override suspend fun getAreas(req: GetAreasRequest): ApiResponse {
        return try {
            withContext(Dispatchers.IO) {
                GetAreasResponse(areas = api.getAreas(locale = req.locale))
                    .apply { resultCode = 200 }
            }
        } catch (e: Throwable) {
            ApiResponse().apply { resultCode = 500 }
        }
    }

    override suspend fun getIndustries(req: GetIndustriesRequest): ApiResponse {
        return try {
            withContext(Dispatchers.IO) {
                GetIndustriesResponse(
                    industries = api.getIndustries(locale = req.locale)
                ).apply { resultCode = 200 }
            }
        } catch (e: Throwable) {
            ApiResponse().apply { resultCode = 500 }
        }
    }
}

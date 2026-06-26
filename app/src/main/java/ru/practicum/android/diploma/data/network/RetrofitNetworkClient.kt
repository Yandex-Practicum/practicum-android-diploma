package ru.practicum.android.diploma.data.network

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.dto.FilterAreaRequest
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.VacanciesRequest
import ru.practicum.android.diploma.data.dto.VacancyDetailsRequest
import ru.practicum.android.diploma.data.dto.VacancyDetailsResponse
import ru.practicum.android.diploma.util.networkConnectivityChecker

class RetrofitNetworkClient(
    private val apiService: PracticumApiService,
    private val context: Context
) : NetworkClient {

    override suspend fun filterAreaRequest(dto: Any): Response {
        if (!networkConnectivityChecker(context) || dto !is FilterAreaRequest) {
            return Response().apply {
                resultCode = if (!networkConnectivityChecker(context)) {
                    NO_CONNECTION_CODE
                } else {
                    BAD_REQUEST_CODE
                }
            }
        }

        return withContext(Dispatchers.IO) {
            try {
                apiService.getAreas().apply { resultCode = SUCCESS_CODE }
            } catch (_: Throwable) {
                Response().apply { resultCode = SERVER_ERROR_CODE }
            }
        }
    }

    override suspend fun searchVacancies(dto: Any): Response {
        val hasConnection = networkConnectivityChecker(context)
        if (!hasConnection || dto !is VacanciesRequest) {
            return Response().apply {
                resultCode = if (!hasConnection) NO_CONNECTION_CODE else BAD_REQUEST_CODE
            }
        }

        return withContext(Dispatchers.IO) {
            try {
                val options = mutableMapOf<String, String>()
                options["text"] = dto.text
                options["page"] = dto.page.toString()
                dto.salary?.let { options["salary"] = it.toString() }
                if (dto.onlyWithSalary) options["only_with_salary"] = "true"
                dto.area?.let { options["area"] = it }
                dto.industry?.let { options["industry"] = it }

                apiService.searchVacancies(options).apply { resultCode = SUCCESS_CODE }
            } catch (_: Throwable) {
                Response().apply { resultCode = SERVER_ERROR_CODE }
            }
        }
    }

    override suspend fun getVacancyDetails(dto: Any): Response {
        val hasConnection = networkConnectivityChecker(context)
        if (!hasConnection || dto !is VacancyDetailsRequest) {
            return Response().apply {
                resultCode = if (!hasConnection) NO_CONNECTION_CODE else BAD_REQUEST_CODE
            }
        }

        return withContext(Dispatchers.IO) {
            try {
                val vacancyDto = apiService.getVacancyDetails(dto.vacancyId)
                VacancyDetailsResponse(vacancyDto).apply { resultCode = SUCCESS_CODE }
            } catch (_: Throwable) {
                Response().apply { resultCode = SERVER_ERROR_CODE }
            }
        }
    }

    companion object {
        private const val SUCCESS_CODE = 200
        private const val BAD_REQUEST_CODE = 400
        private const val SERVER_ERROR_CODE = 500
        private const val NO_CONNECTION_CODE = -1
    }
}

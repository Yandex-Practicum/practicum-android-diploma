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
        return when {
            !networkConnectivityChecker(context) -> Response().apply { resultCode = NO_CONNECTION_CODE }
            dto !is FilterAreaRequest -> Response().apply { resultCode = BAD_REQUEST_CODE }
            else -> executeRequest { apiService.getAreas() }
        }
    }

    override suspend fun searchVacancies(dto: Any): Response {
        return when {
            !networkConnectivityChecker(context) -> Response().apply { resultCode = NO_CONNECTION_CODE }
            dto !is VacanciesRequest -> Response().apply { resultCode = BAD_REQUEST_CODE }
            else -> executeRequest {
                val options = createSearchOptions(dto)
                apiService.searchVacancies(options)
            }
        }
    }

    override suspend fun getVacancyDetails(dto: Any): Response {
        return when {
            !networkConnectivityChecker(context) -> Response().apply { resultCode = NO_CONNECTION_CODE }
            dto !is VacancyDetailsRequest -> Response().apply { resultCode = BAD_REQUEST_CODE }
            else -> executeRequest {
                val vacancyDto = apiService.getVacancyDetails(dto.vacancyId)
                VacancyDetailsResponse(vacancyDto)
            }
        }
    }

    private suspend fun executeRequest(request: suspend () -> Response): Response {
        return withContext(Dispatchers.IO) {
            try {
                request().apply { resultCode = SUCCESS_CODE }
            } catch (_: Throwable) {
                Response().apply { resultCode = SERVER_ERROR_CODE }
            }
        }
    }

    private fun createSearchOptions(dto: VacanciesRequest): Map<String, String> {
        return mutableMapOf<String, String>().apply {
            put("text", dto.text)
            put("page", dto.page.toString())
            dto.salary?.let { put("salary", it.toString()) }
            if (dto.onlyWithSalary) {
                put("only_with_salary", "true")
            }
            dto.area?.let { put("area", it) }
            dto.industry?.let { put("industry", it) }
        }
    }

    companion object {
        private const val SUCCESS_CODE = 200
        private const val BAD_REQUEST_CODE = 400
        private const val SERVER_ERROR_CODE = 500
        private const val NO_CONNECTION_CODE = -1
    }
}

package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import okio.IOException
import ru.practicum.android.diploma.data.filter.country.CountryRequest
import ru.practicum.android.diploma.data.filter.country.response.AreasResponse
import ru.practicum.android.diploma.data.filter.industries.IndustriesRequest
import ru.practicum.android.diploma.data.filter.industries.IndustriesResponse
import ru.practicum.android.diploma.data.filter.region.RegionByIdRequest
import ru.practicum.android.diploma.data.vacancies.details.DetailRequest
import ru.practicum.android.diploma.data.vacancies.dto.VacanciesSearchRequest
import ru.practicum.android.diploma.data.vacancies.response.Response
import ru.practicum.android.diploma.data.vacancies.response.ResponseCodes

class RetrofitNetworkClient(
    private val context: Context,
    private val searchVacanciesApi: SearchVacanciesApi
) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            return createNoConnectionResponse()
        }

        return executeRequest(dto)
    }

    override suspend fun doRequestFilter(dto: Any): Response {
        if (!isConnected()) {
            return createNoConnectionResponse()
        }

        return executeRequestFilter(dto)
    }

    private suspend fun createNoConnectionResponse(): Response {
        return Response().apply { resultCode = ResponseCodes.NO_CONNECTION }
    }

    private fun isValidDto(dto: Any): Boolean {
        return isValidGeneralDto(dto) || isValidFilterDto(dto)
    }

    private fun isValidGeneralDto(dto: Any): Boolean {
        return dto is VacanciesSearchRequest || dto is DetailRequest
    }

    private fun isValidFilterDto(dto: Any): Boolean {
        // Сюда добавить 3 запроса фитров
        return dto is CountryRequest || dto is RegionByIdRequest || dto is IndustriesRequest
    }

    private suspend fun executeRequest(dto: Any): Response {
        if (!isValidDto(dto)) {
            return createErrorResponse()
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = when (dto) {
                    is VacanciesSearchRequest -> {
                        searchVacanciesApi.getListVacancy(dto.queryMap)
                    }

                    is DetailRequest -> {
                        searchVacanciesApi.getVacancyDetail(dto.id)
                    }

                    else -> throw IllegalArgumentException("Invalid DTO type: $dto")
                }
                response.apply { resultCode = ResponseCodes.SUCCESS }

            } catch (e: IOException) {
                Log.e("Exception", e.message.toString())
                Response().apply { resultCode = ResponseCodes.SERVER_ERROR }
            }

        }
    }

    private suspend fun executeRequestFilter(dto: Any): Response {
        if (!isValidDto(dto)) {
            return createErrorResponse()
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = when (dto) {
                    is CountryRequest -> async { getAreas() }
                    is IndustriesRequest -> async { getIndustries() }
                    is RegionByIdRequest -> async {
                        searchVacanciesApi.getAreaId(dto.countryId)
                    }

                    else -> throw IllegalArgumentException("Invalid DTO type: $dto")
                }.await()
                response.apply { resultCode = ResponseCodes.SUCCESS }
            } catch (e: IOException) {
                Response().apply { resultCode = ResponseCodes.SERVER_ERROR }
                throw e
            }
        }
    }

    private suspend fun getIndustries(): Response {
        return try {
            val industries = searchVacanciesApi.getAllIndustries()
            if (industries.isNotEmpty()) {
                IndustriesResponse(industries).apply { resultCode = ResponseCodes.SUCCESS }
            } else {
                // Создание экземпляра вашего класса Response с кодом ошибки сервера
                Response().apply { resultCode = ResponseCodes.SERVER_ERROR }
            }
        } catch (e: IOException) {
            Response().apply { resultCode = ResponseCodes.SERVER_ERROR }
            throw e
        }
    }

    private suspend fun getAreas(): Response {
        return try {
            val areas = searchVacanciesApi.getAllAreas()
            if (areas.isNotEmpty()) {
                AreasResponse(areas).apply { resultCode = ResponseCodes.SUCCESS }
            } else {
                Response().apply { resultCode = ResponseCodes.SERVER_ERROR }
            }
        } catch (e: IOException) {
            Response().apply { resultCode = ResponseCodes.SERVER_ERROR }
            throw e
        }
    }

    private fun createErrorResponse(): Response {
        return Response().apply { resultCode = ResponseCodes.ERROR }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        return capabilities?.run {
            return hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } ?: false
    }
}

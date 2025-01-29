package ru.practicum.android.diploma.common.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.common.data.Mapper
import ru.practicum.android.diploma.common.data.dto.IndustriesResponse
import ru.practicum.android.diploma.common.data.dto.IndustryRequest
import ru.practicum.android.diploma.common.data.dto.Response
import ru.practicum.android.diploma.common.data.dto.SearchVacancyRequest
import ru.practicum.android.diploma.common.util.ConnectivityManager
import ru.practicum.android.diploma.vacancy.data.network.VacancyDetailsRequest
import java.util.Objects

class RetrofitNetworkClient(
    private val headHunterApi: HeadHunterApi,
    private val mapper: Mapper,
    private val connectivityManager: ConnectivityManager
) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response =
        if (!connectivityManager.isConnected()) {
            Response().apply { resultCode = Response.NO_INTERNET_ERROR_CODE }
        } else {
            when (dto) {
                is SearchVacancyRequest -> {
                    withContext(Dispatchers.IO) {
                        try {
                            val resp = headHunterApi.searchVacancies(mapper.map(dto.expression))
                            resp.apply { resultCode = Response.SUCCESS_RESPONSE_CODE }
                        } catch (e: HttpException) {
                            error(e)
                        }
                    }
                }
                is VacancyDetailsRequest -> {
                    withContext(Dispatchers.IO) {
                        try {
                            val resp = headHunterApi.getVacancyDetails(dto.vacancyId)
                            resp.apply { resultCode = Response.SUCCESS_RESPONSE_CODE }
                        } catch (e: HttpException) {
                            error(e)
                        }
                    }
                }
                // <- сюда добавлять запросы для других ручек
                is IndustryRequest -> {
                    withContext(Dispatchers.IO) {
                        try {
                            val list = headHunterApi.getIndustries()
                            val resp = mapper.map(list)
                            resp.apply { resultCode = Response.SUCCESS_RESPONSE_CODE }
                        } catch (e: HttpException) {
                            error(e)
                        }
                    }
                }
                else -> { Response().apply { resultCode = Response.BAD_REQUEST_ERROR_CODE } }
            }
        }

    private fun error(error: HttpException): Response =
        if (error.code() == Response.NOT_FOUND_ERROR_CODE) {
            Log.e("RetrofitNetworkClient", "HTTP error: ${error.message}", error)
            Response().apply { resultCode = Response.NOT_FOUND_ERROR_CODE }
        } else {
            Log.e("RetrofitNetworkClient", "HTTP error: ${error.message}", error)
            Response().apply { resultCode = Response.INTERNAL_SERVER_ERROR_CODE }
        }

}

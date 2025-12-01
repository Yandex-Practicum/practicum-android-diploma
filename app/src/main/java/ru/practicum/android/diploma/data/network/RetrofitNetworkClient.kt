package ru.practicum.android.diploma.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.ErrorCodes
import ru.practicum.android.diploma.data.dto.NetworkResponse
import ru.practicum.android.diploma.data.dto.requests.VacanciesSearchRequest
import ru.practicum.android.diploma.util.NetworkManager
import java.io.IOException

class RetrofitNetworkClient(
    private val apiService: ApiService,
    private val networkManager: NetworkManager
) : NetworkClient {
    override suspend fun findVacancies(dto: VacanciesSearchRequest): NetworkResponse {
        if (!networkManager.isConnected()) {
            return NetworkResponse().apply { resultCode = ErrorCodes.ERROR_NO_INTERNET }
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.findVacancies(
                    dto.query,
                    dto.area,
                    dto.salary,
                    dto.industry,
                    dto.page,
                    dto.onlyWithSalary
                )
                response.apply { resultCode = ErrorCodes.SUCCESS_STATUS }
            } catch (e: HttpException) {
                Log.e(TAG, e.stackTraceToString())
                NetworkResponse().apply { resultCode = e.code() }
            } catch (e: IOException) {
                Log.e(TAG, e.stackTraceToString())
                NetworkResponse().apply { resultCode = ErrorCodes.IO_EXCEPTION }
            }
        }
    }

    override suspend fun getVacancyById(id: String): NetworkResponse {
        if (!networkManager.isConnected()) {
            return NetworkResponse().apply { resultCode = ErrorCodes.ERROR_NO_INTERNET }
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getVacancyById(id)
                response.apply { resultCode = ErrorCodes.SUCCESS_STATUS }
            } catch (e: HttpException) {
                Log.e(TAG, e.stackTraceToString())
                NetworkResponse().apply { resultCode = e.code() }
            } catch (e: IOException) {
                Log.e(TAG, e.stackTraceToString())
                NetworkResponse().apply { resultCode = ErrorCodes.IO_EXCEPTION }
            }
        }
    }

    override suspend fun getAreas(): NetworkResponse {
        if (!networkManager.isConnected()) {
            return NetworkResponse().apply { resultCode = ErrorCodes.ERROR_NO_INTERNET }
        }
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getAreas()
                response.apply { resultCode = ErrorCodes.SUCCESS_STATUS }
            } catch (e: HttpException) {
                Log.e(TAG, e.stackTraceToString())
                NetworkResponse().apply { resultCode = e.code() }
            } catch (e: IOException) {
                Log.e(TAG, e.stackTraceToString())
                NetworkResponse().apply { resultCode = ErrorCodes.IO_EXCEPTION }
            }
        }
    }

    override suspend fun getIndustries(): NetworkResponse {
        if (!networkManager.isConnected()) {
            return NetworkResponse().apply { resultCode = ErrorCodes.ERROR_NO_INTERNET }
        }
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getIndustries()
                response.apply { resultCode = ErrorCodes.SUCCESS_STATUS }
            } catch (e: HttpException) {
                Log.e(TAG, e.stackTraceToString())
                NetworkResponse().apply { resultCode = e.code() }
            } catch (e: IOException) {
                Log.e(TAG, e.stackTraceToString())
                NetworkResponse().apply { resultCode = ErrorCodes.IO_EXCEPTION }
            }
        }
    }

    companion object {
        const val TAG = "RETROFIT_NETWORK_CLIENT"
    }
}

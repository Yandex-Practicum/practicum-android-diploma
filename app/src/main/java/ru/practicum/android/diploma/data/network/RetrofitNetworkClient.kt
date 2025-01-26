package ru.practicum.android.diploma.data.network

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.util.network.CheckNetworkConnect

class RetrofitNetworkClient(
    private val api: HhApi,
    private val context: Context
) : NetworkClient {

    companion object {
        private const val NO_INTERNET_CONNNECTION = -1
        private const val INTERNAL_SERVER_ERROR = 500
        private const val SUCCESSFUL_REQUEST = 200
    }

    private val isConnected = CheckNetworkConnect.isNetworkAvailable(context)

    override suspend fun doRequestVacancies(): Response {
        // Если подключение к интернету отсутствует
        if (!isConnected) {
            return Response().apply { resultCode = NO_INTERNET_CONNNECTION }
        } else {
            return withContext(Dispatchers.IO) {
                try {
                    val response = api.searchVacancies()
                    response.apply { resultCode = SUCCESSFUL_REQUEST }
                } catch (e: HttpException) {
                    Log.w("HttpException", e)
                    Response().apply { resultCode = INTERNAL_SERVER_ERROR }
                }
            }
        }
    }

    override suspend fun doRequestVacancyDetails(vacancyId: String): Response {
        if (!isConnected) {
            return Response().apply { resultCode = NO_INTERNET_CONNNECTION }
        } else {
            return withContext(Dispatchers.IO) {
                try {
                    val response = api.getVacancyDetails(vacancyId)
                    response.apply { resultCode = SUCCESSFUL_REQUEST }
                } catch (e: HttpException) {
                    Log.w("HttpException", e)
                    Response().apply { resultCode = INTERNAL_SERVER_ERROR }
                }
            }
        }
    }
}

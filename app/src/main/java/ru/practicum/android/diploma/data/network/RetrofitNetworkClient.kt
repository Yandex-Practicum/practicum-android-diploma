package ru.practicum.android.diploma.data.network

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.util.ResponseCode
import ru.practicum.android.diploma.util.network.CheckNetworkConnect

class RetrofitNetworkClient(
    private val api: HhApi,
    private val context: Context
) : NetworkClient {

    private val isConnected = CheckNetworkConnect.isNetworkAvailable(context)

    override suspend fun doRequestVacancies(): Response {
        // Если подключение к интернету отсутствует
        if (!isConnected) {
            return Response().apply { resultCode = ResponseCode.NO_INTERNET.code }
        } else {
            return withContext(Dispatchers.IO) {
                try {
                    val response = api.searchVacancies()
                    response.apply { resultCode = ResponseCode.SUCCESS.code }
                } catch (e: HttpException) {
                    Log.w("HttpException", e)
                    Response().apply { resultCode = ResponseCode.SERVER_ERROR.code }
                }
            }
        }
    }

    override suspend fun doRequestVacancyDetails(vacancyId: String): Response {
        if (!isConnected) {
            return Response().apply { resultCode = ResponseCode.NO_INTERNET.code }
        } else {
            return withContext(Dispatchers.IO) {
                try {
                    val response = api.getVacancyDetails(vacancyId)
                    response.apply { resultCode = ResponseCode.SUCCESS.code }
                } catch (e: HttpException) {
                    Log.w("HttpException", e)
                    Response().apply { resultCode = ResponseCode.SERVER_ERROR.code }
                }
            }
        }
    }
}

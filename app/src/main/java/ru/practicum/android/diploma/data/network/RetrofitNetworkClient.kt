package ru.practicum.android.diploma.data.network

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.dto.Request
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.util.network.CheckNetworkConnect

class RetrofitNetworkClient(
    private val api: HhApi,
    private val context: Context
) : NetworkClient {

    private val isConnected = CheckNetworkConnect.isNetworkAvailable(context)

    override suspend fun doRequest(dto: Any): Response {
        // Если подключение к интернету отсутствует
        if (!isConnected) {
            return Response().apply { resultCode = -1 }
        }

        if (dto is Request.VacanciesSearchRequest) {
            return withContext(Dispatchers.IO) {
                try {
                    val response = api.searchVacancies()
                    response.apply { resultCode = 200 }
                } catch (e: HttpException) {
                    Log.w("HttpException", e)
                    Response().apply { resultCode = 500 }
                }
            }
        }

        if (dto is Request.VacancyDetailsRequest) {
            return withContext(Dispatchers.IO) {
                try {
                    val response = api.getVacancyDetails(dto.vacancyId)
                    response.apply { resultCode = 200 }
                } catch (e: HttpException) {
                    Log.w("HttpException", e)
                    Response().apply { resultCode = 500 }
                }
            }
        }

        // Если запрос составлен неверно - возвращает ответ с кодом ошибки 400
        // Лучше заменить эту конструкцию с if-ами на when
        return Response().apply { resultCode = 400 }
    }
}

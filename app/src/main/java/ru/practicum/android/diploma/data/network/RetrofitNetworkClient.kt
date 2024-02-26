package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.Response
import ru.practicum.android.diploma.data.ResponseCodes
import ru.practicum.android.diploma.data.vacancydetail.dto.DetailRequest
import ru.practicum.android.diploma.data.vacancylist.dto.VacanciesSearchRequest

class RetrofitNetworkClient(
    private val context: Context,
    private val jobVacancySearchApi: JobVacancySearchApi
) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = ResponseCodes.NO_CONNECTION }
        }

        if ((dto !is VacanciesSearchRequest)
            && (dto !is DetailRequest)
        ) {
            Log.d("StateSearch", "Упали не дошли на ошибке")
            return Response().apply { resultCode = ResponseCodes.ERROR }
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = when (dto) {
                    is VacanciesSearchRequest -> async { jobVacancySearchApi.getVacancyListString(dto.queryMap) }
                    is DetailRequest -> async { jobVacancySearchApi.getVacancyDetail(dto.id) }
                    else -> async { jobVacancySearchApi.getVacancyListString((dto as VacanciesSearchRequest).queryMap) }
                }.await()
                response.apply { resultCode = ResponseCodes.SUCCESS }
            } catch (e: Throwable) {
                Response().apply { resultCode = ResponseCodes.SERVER_ERROR }
            }
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
}

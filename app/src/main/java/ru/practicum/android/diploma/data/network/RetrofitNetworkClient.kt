package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.telephony.DisconnectCause.SERVER_ERROR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.Constant.NO_CONNECTIVITY_MESSAGE
import ru.practicum.android.diploma.data.Constant.SUCCESS_RESULT_CODE
import java.io.IOException

class RetrofitNetworkClient(
    private val service: HhApi,
    private val context: Context
) : NetworkClient {

    override suspend fun search(dto: JobSearchRequest): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = NO_CONNECTIVITY_MESSAGE }
        }
        return withContext(Dispatchers.IO) {
            try {
                val response = service.jobSearch(dto.request)
                response.apply { resultCode = SUCCESS_RESULT_CODE }
            } catch (e: IOException) {
                e.printStackTrace()
                Response().apply { resultCode = SERVER_ERROR }
            }
        }
    }

    override suspend fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = NO_CONNECTIVITY_MESSAGE }
        }
        var response = Response()
        return try {
            when (dto) {
                is DetailVacancyRequest -> withContext(Dispatchers.IO) {
                    response = service.getDetailVacancy(vacancyId = dto.id)
                    response.apply { resultCode = SUCCESS_RESULT_CODE }
                }

                is SimilarRequest -> withContext(Dispatchers.IO) {
                    response = service.similarVacancy(dto.id)
                    response.apply { resultCode = SUCCESS_RESULT_CODE }
                }

                else -> {
                    response.apply { resultCode = SERVER_ERROR }
                }
            }
        } catch (exception: HttpException) {
            Response().apply { resultCode = exception.code() }

        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
    }

}

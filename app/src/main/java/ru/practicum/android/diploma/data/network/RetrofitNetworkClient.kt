package ru.practicum.android.diploma.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.dto.AreasRequest
import ru.practicum.android.diploma.data.dto.IndustriesRequest
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.VacanciesRequest
import ru.practicum.android.diploma.data.dto.VacancyDetailsRequest
import ru.practicum.android.diploma.data.dto.toMap
import ru.practicum.android.diploma.util.NetworkConnectionChecker
import java.io.IOException

class RetrofitNetworkClient(
    private val apiService: ApiService,
    private val networkConnectionChecker: NetworkConnectionChecker
) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response<out Any> {
        if (!networkConnectionChecker.isConnected()) {
            return Response(data = null, resultCode = -1)
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = when (dto) {
                    is VacanciesRequest -> apiService.getVacancies(dto.toMap())
                    is VacancyDetailsRequest -> apiService.getVacancyDetails(dto.id)
                    is AreasRequest -> apiService.getAreas()
                    is IndustriesRequest -> apiService.getIndustries()
                    else -> null
                }
                if (response == null) {
                    Response(data = null, resultCode = 400)
                } else {
                    Response(data = response, resultCode = 200)
                }
            } catch (e: IOException) {
                Log.w(TAG, "Network request failed", e)
                Response(data = null, resultCode = -1)
            } catch (e: HttpException) {
                Response(data = null, resultCode = e.code())
            }
        }
    }

    private companion object {
        const val TAG = "RetrofitNetworkClient"
    }
}

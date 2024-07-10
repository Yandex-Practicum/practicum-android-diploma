package ru.practicum.android.diploma.vacancydetails.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.common.data.NetworkClient
import ru.practicum.android.diploma.common.data.ResponseBase
import ru.practicum.android.diploma.common.domain.BadRequestError
import ru.practicum.android.diploma.common.domain.NoInternetError
import ru.practicum.android.diploma.common.domain.ServerInternalError
import ru.practicum.android.diploma.vacancydetails.data.dto.DetailsRequest
import ru.practicum.android.diploma.vacancydetails.data.dto.DetailsResponse
import ru.practicum.android.diploma.vacancydetails.domain.models.DetailsNotFoundType
import java.net.HttpURLConnection

class DetailsRetrofitNetworkClient(
    private val apiService: DetailsHhApiService,
    private val context: Context
) : NetworkClient {

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        if (capabilities != null) {
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }
        return false
    }

    override suspend fun doRequest(dto: Any): ResponseBase<Any?> {
        if (!isConnected()) {
            return ResponseBase(NoInternetError())
        }
        return withContext(Dispatchers.IO) {
            try {
                when (dto) {
                    is DetailsRequest -> {
                        val responce = apiService.getVacancyDetails(dto.expression, dto.page, dto.perpage)
                        when (responce.code()) {
                            HttpURLConnection.HTTP_OK -> {
                                val detailVacancyResponse = responce.body()
                                if (detailVacancyResponse == null) {
                                    ResponseBase(DetailsNotFoundType())
                                } else {
                                    DetailsResponse(
                                        detailVacancyResponse.found,
                                        detailVacancyResponse.page,
                                        detailVacancyResponse.pages,
                                        detailVacancyResponse.perPage,
                                        detailVacancyResponse.items
                                    )
                                }
                            }
                            HttpURLConnection.HTTP_NOT_FOUND -> ResponseBase(DetailsNotFoundType())
                            else -> ResponseBase(BadRequestError())
                        }
                    }
                    else -> ResponseBase(BadRequestError())
                }
            } catch (e: HttpException) {
                e.message?.let { Log.e("Http", it) }
                ResponseBase(ServerInternalError())
            }
        }
    }

}

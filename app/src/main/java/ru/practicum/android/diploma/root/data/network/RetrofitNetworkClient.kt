package ru.practicum.android.diploma.root.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.features.vacancydetails.data.models.VacancyDetailsDto
import ru.practicum.android.diploma.features.vacancydetails.data.models.VacancyDetailsRequest
import ru.practicum.android.diploma.root.data.network.models.NetworkResultCode
import ru.practicum.android.diploma.root.data.network.models.Response

class RetrofitNetworkClient(
    private val api: HeadHunterApi,
    private val context: Context
) : NetworkSearch {

    override suspend fun getVacancyById(dto: VacancyDetailsRequest): Response<VacancyDetailsDto> {

        if (isConnected() == false) {
            return Response(resultCode = NetworkResultCode.CONNECTION_ERROR, data = null)
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = api.getVacancyById(vacancyId = dto.id)
                Response(resultCode = NetworkResultCode.SUCCESS, response )
            } catch (e: Throwable) {
                Response(resultCode = NetworkResultCode.SERVER_ERROR, data = null)
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
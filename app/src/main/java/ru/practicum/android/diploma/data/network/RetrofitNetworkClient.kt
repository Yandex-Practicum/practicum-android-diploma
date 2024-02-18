package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.VacanciesSearchRequest

class RetrofitNetworkClient(
    private val context: Context
) : NetworkClient {

    private val hhBaseUrl = "https://api.hh.ru"

    private val retrofit = Retrofit.Builder()
        .baseUrl(hhBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val jobVacancySearchApi = retrofit.create(JobVacancySearchApi::class.java)

    override suspend fun executeNetworkRequest(dto: Any): Response {
        if (isConnected() == false) {
            return Response().apply { resultCode = -1 }
        }
        if(dto !is VacanciesSearchRequest){
            return Response().apply{resultCode = 400}
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = jobVacancySearchApi.search(dto.text)
                response.apply { resultCode = 200 }
            } catch (e: Throwable) {
                Response().apply { resultCode = 500 }
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

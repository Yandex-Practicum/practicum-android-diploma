package ru.practicum.android.diploma.search.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.search.data.NetworkClient
import ru.practicum.android.diploma.search.data.dto.Response
import ru.practicum.android.diploma.search.data.dto.SearchRequest

class RetrofitNetworkClient(private val api: Api, private val context: Context) : NetworkClient {

    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun doRequest(dto: Any): Response {
        if (isConnected() == false) {
            return Response().apply { resultCode = -1 }
        }
        if (dto !is SearchRequest) {
            return Response().apply { resultCode = 400 }
        }
        return withContext(Dispatchers.IO){
            try {
                val responce = api.search(dto.expression)
                responce.apply { resultCode = 200 }
            } catch (e: Throwable) {
                Response().apply { resultCode = 500 }

            }
        }


    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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
    companion object{
        const val BASE_URL = "https://api.hh.ru"
    }


}
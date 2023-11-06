package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.SearchRequest
import java.io.IOException
import okhttp3.logging.HttpLoggingInterceptor


class RetrofitNetworkClient(private val api: ApiService, private val context: Context) : NetworkClient {

    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = -1 }
        }
        if (dto !is SearchRequest) {
            return Response().apply { resultCode = 400 }
        }
        return withContext(Dispatchers.IO) {
            try {
                val response = api.search(dto.expression, 0, 10)
                Log.d("RetrofitNetworkClient", "Response: $response")
                response.apply { resultCode = 200 }
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
}

//class RetrofitNetworkClient(private val api: ApiService, private val context: Context) : NetworkClient {
//
//    @RequiresApi(Build.VERSION_CODES.M)
//    override suspend fun doRequest(dto: Any): Response {
//        if (!isConnected()) {
//            return Response().apply { resultCode = -1 }
//        }
//        if (dto !is SearchRequest) {
//            return Response().apply { resultCode = 400 }
//        }
//        return withContext(Dispatchers.IO) {
//            try {
//                val client = createOkHttpClient()
//                val response = client.newCall(api.search(dto.expression, 0, 10)).execute()
//                Log.d("RetrofitNetworkClient", "Request URL: ${response.request.url}")
//                Log.d("RetrofitNetworkClient", "Response: ${response.body?.string()}")
//                response.use {
//                    if (response.isSuccessful) {
//                        return Response().apply { resultCode = 200 }
//                    } else {
//                        return Response().apply { resultCode = 500 }
//                    }
//                }
//            } catch (e: IOException) {
//                return Response().apply { resultCode = 500 }
//            }
//        }
//    }
//
//
//
//    @RequiresApi(Build.VERSION_CODES.M)
//    private fun isConnected(): Boolean {
//        val connectivityManager =
//            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val capabilities =
//            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
//        if (capabilities != null) {
//            when {
//                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
//                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
//                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
//            }
//        }
//        return false
//    }
//
//    private fun createOkHttpClient(): OkHttpClient {
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.level = HttpLoggingInterceptor.Level.BODY
//        return OkHttpClient.Builder()
//            .addInterceptor(interceptor)
//            .build()
//    }
//}
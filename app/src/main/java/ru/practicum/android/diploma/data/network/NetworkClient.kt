package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.google.gson.JsonParseException
import retrofit2.HttpException
import java.io.IOException

class NetworkClient(
    private val context: Context,
) {
    suspend fun <T> execute(block: suspend () -> T): ApiResponse<T> {
        if (!isConnected()) {
            return ApiResponse.Error(STATUS_CODE_NO_INTERNET)
        }

        return try {
            val result = block()
            ApiResponse.Success(result)
        } catch (e: IOException) {
            Log.e(TAG, "IOException caught", e)
            ApiResponse.Error(STATUS_CODE_NO_INTERNET)
        } catch (e: HttpException) {
            Log.e(TAG, "HttpException caught", e)
            ApiResponse.Error(e.code())
        } catch (e: JsonParseException) {
            Log.e(TAG, "Json parsing error", e)
            ApiResponse.Error(STATUS_CODE_500)
        } catch (e: IllegalStateException) {
            Log.e(TAG, "Illegal state", e)
            ApiResponse.Error(STATUS_CODE_500)
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.run {
            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } ?: false
    }

    companion object {
        private const val STATUS_CODE_500 = 500
        private const val STATUS_CODE_NO_INTERNET = -1
        private const val TAG = "NetworkClient"
    }
}

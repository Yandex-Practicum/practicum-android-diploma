package ru.practicum.android.diploma.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InternetConnectionChecker {

    suspend fun checkConnectionAndShowToast(context: Context) {
        val isConnected = withContext(Dispatchers.IO) {
            isInternetAvailable(context)
        }

        val message = if (isConnected) {
            "подключение к интернету есть"
        } else {
            "подключение к интернету нет"
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return connectivityManager.activeNetwork?.let { network ->
            connectivityManager.getNetworkCapabilities(network)?.let { capabilities ->
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
            } ?: false
        } ?: false
    }
}

package ru.practicum.android.diploma.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object NetworkHelper {
    fun isConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val capabilities = connectivityManager.getNetworkCapabilities(
            connectivityManager.activeNetwork
        )

        return capabilities?.run {
            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        } ?: false
    }
}

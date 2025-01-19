package ru.practicum.android.diploma.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun isConnected(context: Context): Boolean {
    var isConnected = false
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        isConnected = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }
    return isConnected
}

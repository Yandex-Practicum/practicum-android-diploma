package ru.practicum.android.diploma.domain.impl.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import ru.practicum.android.diploma.data.network.NetworkChecker

class NetworkCheckerImpl(private val context: Context) : NetworkChecker {
    @SuppressLint("MissingPermission")
    override fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        return capabilities?.run {
            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } ?: false
    }
}

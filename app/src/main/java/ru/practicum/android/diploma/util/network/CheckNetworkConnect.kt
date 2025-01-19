/**Функция для проверки подключения к интернету
 * требуется указать контекст
 */

package ru.practicum.android.diploma.util.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object CheckNetworkConnect {
    fun isNetworkAvailable(context: Context): Boolean {
        return (context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager)
            ?.activeNetwork
            ?.let { network ->
                (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                    .getNetworkCapabilities(network)
                    ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            } ?: false
    }
}

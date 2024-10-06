package ru.practicum.android.diploma.commonutils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.commonutils.Resource
import ru.practicum.android.diploma.ui.R

fun Context.isConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    return (capabilities != null
        && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
            || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)))
}

fun <T, S> Context.executeNetworkRequest(
    request: suspend () -> T,
    successHandler: (T) -> Resource<S>
): Flow<Resource<S>> =
    flow {
        val response: T = request.invoke()
        when ((response as Response).resultCode) {
            HttpStatus.NO_INTERNET -> {
                emit(
                    Resource.Error(
                        getString(R.string.check_network_connection)
                    )
                )
            }

            HttpStatus.OK -> {
                with(response as T) {
                    emit(successHandler(response))
                }
            }

            HttpStatus.CLIENT_ERROR -> {
                emit(
                    Resource.Error(
                        getString(
                            R.string.request_was_not_accepted,
                            response.resultCode,
                        )
                    )
                )
            }

            HttpStatus.CLIENT_ERROR_404 -> {
                emit(
                    Resource.Error(
                        getString(
                            R.string.request_was_not_accepted_404
                        )
                    )
                )
            }

            HttpStatus.SERVER_ERROR -> {
                emit(
                    Resource.Error(
                        getString(
                            R.string.unexpected_error,
                            response.resultCode
                        )
                    )
                )
            }
        }
    }

package ru.practicum.android.diploma.search.data.network.interceptors

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val requestTimestamp = System.nanoTime()
        Log.d(
            "NETWORK",
            "Sending request ${request.url()} on ${chain.connection()}\n" +
                "${request.headers()}\n${request.body()}"
        )

        val response = chain.proceed(request)

        val responseTimestamp = System.nanoTime()
        Log.d(
            "NETWORK",
            "Received response for ${response.request().url()}" +
                " in ${responseTimestamp - requestTimestamp / 1000.0}" +
                "${response.headers()}\n${response.body()}"
        )

        return response
    }
}

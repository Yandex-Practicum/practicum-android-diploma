package ru.practicum.android.diploma.core.data.network

import okhttp3.Interceptor
import okhttp3.Response

class UserAgentInterceptor(private val userAgent: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val withUserAgent = chain.request()
            .newBuilder()
            .header("HH-User-Agent", userAgent)
            .build()
        return chain.proceed(withUserAgent)
    }
}

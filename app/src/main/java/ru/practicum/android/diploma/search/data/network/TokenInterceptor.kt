package ru.practicum.android.diploma.search.data.network

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val accessToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .addHeader("HH-User-Agent", "DiplomaHhApp (https://example.com/page)")
            .build()
        return chain.proceed(request)
    }
}


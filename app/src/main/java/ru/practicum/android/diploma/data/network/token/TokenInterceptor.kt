package ru.practicum.android.diploma.data.network.token

import okhttp3.Interceptor

class TokenInterceptor(
    private val accessTokenProvider: AccessTokenProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val token = accessTokenProvider.getAccessToken()

        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .addHeader("HH-User-Agent", "MyApp (di.burmistrov@gmail.com)")
            .build()
        return chain.proceed(request)
    }
}

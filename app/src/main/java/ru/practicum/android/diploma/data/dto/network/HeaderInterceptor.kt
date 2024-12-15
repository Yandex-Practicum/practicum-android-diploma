package ru.practicum.android.diploma.data.dto.network

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.run {
            proceed(
                request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer $TOKEN")
                    .addHeader("HH-User-Agent", "$APPLICATION_NAME($EMAIL)")
                    .build()
            )
        }
    }

}

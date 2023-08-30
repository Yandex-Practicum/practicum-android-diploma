package ru.practicum.android.diploma.root.data.network

import okhttp3.Interceptor
import okhttp3.Response
import ru.practicum.android.diploma.BuildConfig

class HeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("Authorization", "Bearer ${BuildConfig.HH_ACCESS_TOKEN}")
                .removeHeader("User-Agent")
                .addHeader("HH-User-Agent", "MyApp/1.0 (my-app-feedback@example.com)")
                .build()
        )
    }
}
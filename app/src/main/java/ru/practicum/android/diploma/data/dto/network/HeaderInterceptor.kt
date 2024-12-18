package ru.practicum.android.diploma.data.dto.network

import okhttp3.Interceptor
import okhttp3.Response
import ru.practicum.android.diploma.BuildConfig

class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.run {
            proceed(
                request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer ${BuildConfig.HH_ACCESS_TOKEN}")
                    .addHeader("HH-User-Agent", "VacancyApp/1.0 (somedevmail@gmail.com)")
                    .build()
            )
        }
    }

}

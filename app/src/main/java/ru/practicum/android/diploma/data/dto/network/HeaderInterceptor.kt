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
                    .addHeader("Authorization", "Bearer APPLSOUV4KKTH3JJIMN3R8OLNI8BF32KK3SIBG2GRQEC1A659BT2E3K5DUAF5QV3")
                    .addHeader("HH-User-Agent", "VacancyApp/1.0 (somedevmail@gmail.com)")
                    .build()
            )
        }
    }

}

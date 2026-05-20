package ru.practicum.android.diploma.data.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import ru.practicum.android.diploma.domain.api.UserDataRepository

class AuthInterceptor(private val userDataRepository: UserDataRepository) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = userDataRepository.getAuthToken()

        val requestBuilder = if (token != null) {
            originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
        } else {
            Log.e("AuthInterceptor", "Token is null")
            originalRequest.newBuilder()
        }

        val request = requestBuilder
            .header("Content-Type", "application/json")
            .build()
        return chain.proceed(request)
    }
}

package ru.practicum.android.diploma.data.network

import okhttp3.logging.HttpLoggingInterceptor
import ru.practicum.android.diploma.BuildConfig

fun createLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
        level = when {
            BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
            else -> HttpLoggingInterceptor.Level.BASIC
        }
    }
}

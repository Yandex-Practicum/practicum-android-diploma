package ru.practicum.android.diploma.search.data.network

import okhttp3.OkHttpClient

fun provideOkHttpClient(tokenProvider: () -> String): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(tokenProvider))
        .build()
}

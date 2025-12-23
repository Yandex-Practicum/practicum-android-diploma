package ru.practicum.android.diploma.search.data.network

import okhttp3.OkHttpClient

fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder().build()
}

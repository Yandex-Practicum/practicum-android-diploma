package ru.practicum.android.diploma.core.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit

class NetworkClientImpl(retrofit: Retrofit, okHttpClient: OkHttpClient): NetworkClient {

    override suspend fun doRequest(dtoRequest: Any) {
        TODO("Not yet implemented")
    }

}

package ru.practicum.android.diploma.di

import com.google.gson.Gson
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.network.ApiService
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient

val dataModule = module {

    single<ApiService> {
        Retrofit.Builder()
            .baseUrl("baseUrl: https://android-diploma.education-services.ru")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    factory { Gson() }

    single<NetworkClient> {
        RetrofitNetworkClient(get(), get())
    }

}

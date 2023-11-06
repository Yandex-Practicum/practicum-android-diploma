package ru.practicum.android.diploma.core.di


import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.ResourceProvider
import ru.practicum.android.diploma.data.ResourceProviderImpl

import ru.practicum.android.diploma.data.network.ApiService
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient


val dataModule = module {
    factory { Gson() }

    single<ApiService> {
        Retrofit.Builder()
            .baseUrl("https://api.hh.ru")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    single<NetworkClient> { RetrofitNetworkClient(get(), get()) }

    single<ResourceProvider> { ResourceProviderImpl(androidContext()) }
}


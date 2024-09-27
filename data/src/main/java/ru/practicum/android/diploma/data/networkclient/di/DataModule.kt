package ru.practicum.android.diploma.data.networkclient.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.networkclient.data.NetworkClient
import ru.practicum.android.diploma.data.networkclient.data.network.HHApiService
import ru.practicum.android.diploma.data.networkclient.data.network.RetrofitNetworkClient

val searchDataModule = module {

    single<HHApiService> {
        Retrofit.Builder().baseUrl("https://api.hh.ru/").addConverterFactory(GsonConverterFactory.create()).build()
            .create(HHApiService::class.java)
    }
    single<NetworkClient> {
        RetrofitNetworkClient(get(), androidContext())
    }
}

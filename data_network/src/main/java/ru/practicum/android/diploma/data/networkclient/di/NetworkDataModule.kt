package ru.practicum.android.diploma.data.networkclient.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.networkclient.api.NetworkClient
import ru.practicum.android.diploma.data.networkclient.api.network.HHApiService
import ru.practicum.android.diploma.data.networkclient.api.network.RetrofitNetworkClient

val networkDataModule = module {

    single<HHApiService> {
        Retrofit.Builder().baseUrl("https://api.hh.ru/").addConverterFactory(GsonConverterFactory.create()).build()
            .create(HHApiService::class.java)
    }
    single<NetworkClient> {
        RetrofitNetworkClient(get(), androidContext())
    }
}

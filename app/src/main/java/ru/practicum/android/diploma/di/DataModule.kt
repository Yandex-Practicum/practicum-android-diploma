package ru.practicum.android.diploma.di

import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.features.vacancydetails.data.models.VacancyDetailsMapper
import ru.practicum.android.diploma.root.data.network.HeadHunterApi
import ru.practicum.android.diploma.root.data.network.NetworkSearch
import ru.practicum.android.diploma.root.data.network.RetrofitNetworkClient

val dataModule = module {

    single<HeadHunterApi> {
        Retrofit.Builder()
            .baseUrl("https://api.hh.ru")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HeadHunterApi::class.java)
    }

    single<NetworkSearch> {
        RetrofitNetworkClient(api = get(), context = get())
    }

    single<VacancyDetailsMapper>  {
        VacancyDetailsMapper()
    }

}
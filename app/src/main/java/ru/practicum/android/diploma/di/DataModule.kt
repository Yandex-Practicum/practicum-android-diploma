package ru.practicum.android.diploma.di

import androidx.room.Room
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.db.AppDataBase
import ru.practicum.android.diploma.search.data.NetworkClient
import ru.practicum.android.diploma.search.data.network.Api
import ru.practicum.android.diploma.search.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.search.data.ResourceProvider
import ru.practicum.android.diploma.search.data.ResourceProviderImpl

val dataModule = module {
    factory { Gson() }

    single<Api> {
        Retrofit.Builder()
            .baseUrl("https://api.hh.ru")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    singleOf(::RetrofitNetworkClient).bind<NetworkClient>()

    singleOf(::ResourceProviderImpl).bind<ResourceProvider>()

    single {
        Room.databaseBuilder(androidContext(), AppDataBase::class.java, "database.db").build()
    }
    //TODO "add name interface Api":
    // single<name interface> {
    Retrofit.Builder()
        .baseUrl("https://api.hh.ru/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    //TODO "add name interface Api":
    // .create(name interface::class.java)
    // }

    single {
        Room.databaseBuilder(androidContext(), AppDataBase::class.java, "database.db").build()
    }
}
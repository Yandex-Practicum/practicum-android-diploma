package ru.practicum.android.diploma.di

import androidx.room.Room
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import ru.practicum.android.diploma.data.db.DataBase
import ru.practicum.android.diploma.data.network.HhApiService
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient

private val json = Json {
    ignoreUnknownKeys = true
}

val dataModule = module {

    single<HhApiService> {
        Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create(HhApiService::class.java)
    }

    factory<NetworkClient> {
        RetrofitNetworkClient(androidContext(), get())
    }

    single {
        Room.databaseBuilder(androidContext(), DataBase::class.java, "database.db")
            .fallbackToDestructiveMigration(false)
            .build()
    }
}

object ApiConfig {
    const val BASE_URL = "https://api.hh.ru/"
}

package ru.practicum.android.diploma.core.di

import androidx.room.Room
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.core.data.database.AppDatabase
import ru.practicum.android.diploma.core.data.network.NetworkClient
import ru.practicum.android.diploma.core.data.network.NetworkClientImpl
import ru.practicum.android.diploma.core.data.network.NetworkConnectivity
import ru.practicum.android.diploma.core.data.network.NetworkConnectivityImpl

private const val BASE_URL = "https://android-diploma.education-services.ru/"
private const val DBNAME = "diploma.db"

val coreModule = module {
    single {
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    single {
        OkHttpClient
            .Builder()
            .build()
    }

    single<NetworkConnectivity> {
        NetworkConnectivityImpl(androidContext())
    }

    single<NetworkClient> {
        NetworkClientImpl(get(), get())
    }

    single<AppDatabase> {
        Room
            .databaseBuilder(androidContext(), AppDatabase::class.java, DBNAME)
            .build()
    }
}

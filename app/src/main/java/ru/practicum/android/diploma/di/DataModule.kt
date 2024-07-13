package ru.practicum.android.diploma.di

import androidx.room.Room
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.favourites.data.db.MainDB
import ru.practicum.android.diploma.search.data.network.JobApiService
import ru.practicum.android.diploma.search.data.network.interceptors.LoggingInterceptor
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.data.network.RetrofitClient
import ru.practicum.android.diploma.sharing.data.ExternalNavigatorImpl
import ru.practicum.android.diploma.sharing.domain.ExternalNavigator

val dataModule = module {

    single<NetworkClient> {
        RetrofitClient(
            jobApiService = get(),
            context = androidContext()
        )
    }

    single<JobApiService> {
        val client = OkHttpClient.Builder()
            .addInterceptor(LoggingInterceptor)
            .build()

        Retrofit.Builder()
            .baseUrl(BuildConfig.HH_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JobApiService::class.java)
    }

    single {
        Room.databaseBuilder(
            context = androidContext(),
            klass = MainDB::class.java,
            name = BuildConfig.DATABASE_FILE_NAME
        ).build()
    }

    single<ExternalNavigator> {
        ExternalNavigatorImpl(context = androidContext())
    }
}

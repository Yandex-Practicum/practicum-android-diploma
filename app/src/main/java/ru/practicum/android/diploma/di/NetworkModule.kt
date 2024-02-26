package ru.practicum.android.diploma.di

import androidx.room.Room
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.ApiEndpoints
import ru.practicum.android.diploma.app.App
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.network.JobVacancySearchApi
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient

val networkModule = module {

    single<OkHttpClient> {
        OkHttpClient
            .Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
            )
            .build()
    }

    single<Retrofit> {
        val client = get<OkHttpClient>()
        Retrofit.Builder()
            .baseUrl(ApiEndpoints.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<JobVacancySearchApi> {
        val retrofit = get<Retrofit>()
        retrofit.create(JobVacancySearchApi::class.java)
    }

    single<NetworkClient> {
        RetrofitNetworkClient(androidContext(), get())
    }

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "get_vacancy_db")
            .build()
    }
}

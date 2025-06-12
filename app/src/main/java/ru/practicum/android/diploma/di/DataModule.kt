package ru.practicum.android.diploma.di

import androidx.room.Room
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.converters.VacanciesDbConverter
import ru.practicum.android.diploma.data.network.HHApiService
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.util.API_BASE
import ru.practicum.android.diploma.data.db.dao.VacanciesDao

val dataModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "hh_database.db")
            .build()
    }

    single<VacanciesDao> {
        get<AppDatabase>().vacanciesDao()
    }

    single {
        VacanciesDbConverter()
    }

    single<HHApiService> {
        Retrofit.Builder()
            .baseUrl(API_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getHttpClient())
            .build()
            .create(HHApiService::class.java)
    }

    single<NetworkClient> {
        RetrofitNetworkClient(get(), get())
    }
}

private fun getHttpClient(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)

    return OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val original = chain.request()
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${BuildConfig.HH_ACCESS_TOKEN}")
                    .addHeader("HH-User-Agent", "RabotaDlyaAboby (test@yandex.ru)")
                    .method(original.method, original.body)
                    .build()
                return chain.proceed(newRequest)
            }
        })
        .build()
}

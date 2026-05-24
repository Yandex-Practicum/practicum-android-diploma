package ru.practicum.android.diploma.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.network.DiplomaApi
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.NetworkClientImpl
import ru.practicum.android.diploma.data.network.NetworkConnectionChecker
import ru.practicum.android.diploma.data.network.NetworkConnectionCheckerImpl
import ru.practicum.android.diploma.data.network.NetworkConstants
import ru.practicum.android.diploma.data.repositories.VacanciesRepositoryImpl
import ru.practicum.android.diploma.domain.api.VacanciesRepository

val dataModule = module {

    single {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .header(AUTHORIZATION_HEADER, "$AUTHORIZATION_BEARER_PREFIX ${BuildConfig.API_ACCESS_TOKEN}")
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(NetworkConstants.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<DiplomaApi> {
        get<Retrofit>().create(DiplomaApi::class.java)
    }

    single<NetworkConnectionChecker> {
        NetworkConnectionCheckerImpl(context = get())
    }

    single<NetworkClient> {
        NetworkClientImpl(connectionChecker = get())
    }

    single<VacanciesRepository> {
        VacanciesRepositoryImpl(api = get(), networkClient = get())
    }
}

private const val AUTHORIZATION_HEADER = "Authorization"
private const val AUTHORIZATION_BEARER_PREFIX = "Bearer"

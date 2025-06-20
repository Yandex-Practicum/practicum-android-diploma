package ru.practicum.android.diploma.di

import androidx.room.Room
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.converters.VacanciesDbConverter
import ru.practicum.android.diploma.data.db.dao.VacanciesDao
import ru.practicum.android.diploma.data.impl.TokenProviderImpl
import ru.practicum.android.diploma.data.network.AuthInterceptor
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.NetworkClientInterface
import ru.practicum.android.diploma.data.network.TokenProvider
import ru.practicum.android.diploma.data.vacancy.HhApi
import ru.practicum.android.diploma.data.vacancy.SearchVacanciesNetworkDataSource
import ru.practicum.android.diploma.data.vacancy.VacancyDetailsNetworkDataSource
import ru.practicum.android.diploma.ui.vacancy.HtmlParser
import ru.practicum.android.diploma.ui.vacancy.VacancyDetailsMapper
import ru.practicum.android.diploma.util.API_BASE

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

    single<NetworkClientInterface> {
        NetworkClient(androidContext(), get())
    }

    single<TokenProvider> {
        TokenProviderImpl()
    }

    single {
        AuthInterceptor(get())
    }
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<AuthInterceptor>())
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(API_BASE)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<HhApi> {
        get<Retrofit>().create(HhApi::class.java)
    }

    single {
        SearchVacanciesNetworkDataSource(get())
    }

    single {
        VacancyDetailsNetworkDataSource(get(), get())
    }

    single { HtmlParser() }

    single { VacancyDetailsMapper(get()) }
}

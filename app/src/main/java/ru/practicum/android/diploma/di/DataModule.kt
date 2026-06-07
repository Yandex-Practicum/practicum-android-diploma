package ru.practicum.android.diploma.di

import android.content.Context
import androidx.room.Room
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.dao.VacancyDao
import ru.practicum.android.diploma.data.network.DiplomaApi
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.NetworkClientImpl
import ru.practicum.android.diploma.data.network.NetworkConnectionChecker
import ru.practicum.android.diploma.data.network.NetworkConnectionCheckerImpl
import ru.practicum.android.diploma.data.network.NetworkConstants
import ru.practicum.android.diploma.data.repositories.AreasRepositoryImpl
import ru.practicum.android.diploma.data.repositories.DetailsRepositoryImpl
import ru.practicum.android.diploma.data.repositories.FavoritesRepositoryImpl
import ru.practicum.android.diploma.data.repositories.FilterRepositoryImpl
import ru.practicum.android.diploma.data.repositories.IndustriesRepositoryImpl
import ru.practicum.android.diploma.data.repositories.VacanciesRepositoryImpl
import ru.practicum.android.diploma.data.storage.filter.FilterSettingsStorage
import ru.practicum.android.diploma.data.storage.filter.SharedPreferencesFilterSettingsStorage
import ru.practicum.android.diploma.domain.api.AreasRepository
import ru.practicum.android.diploma.domain.api.DetailsRepository
import ru.practicum.android.diploma.domain.api.FavoritesRepository
import ru.practicum.android.diploma.domain.api.FilterRepository
import ru.practicum.android.diploma.domain.api.IndustriesRepository
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

    single {
        get<Context>().getSharedPreferences(FILTER_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    single<FilterSettingsStorage> {
        SharedPreferencesFilterSettingsStorage(sharedPreferences = get())
    }

    single<VacanciesRepository> {
        VacanciesRepositoryImpl(api = get(), networkClient = get())
    }

    single<FilterRepository> {
        FilterRepositoryImpl(storage = get())
    }

    single<DetailsRepository> {
        DetailsRepositoryImpl(api = get(), networkClient = get())
    }

    single<AreasRepository> {
        AreasRepositoryImpl(api = get(), networkClient = get())
    }

    single<AppDatabase> {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            DATABASE_NAME,
        ).build()
    }

    single<VacancyDao> {
        get<AppDatabase>().vacancyDao()
    }

    single<FavoritesRepository> {
        FavoritesRepositoryImpl(vacancyDao = get())
    }

    single<IndustriesRepository> {
        IndustriesRepositoryImpl(api = get(), networkClient = get())
    }
}

private const val AUTHORIZATION_HEADER = "Authorization"
private const val AUTHORIZATION_BEARER_PREFIX = "Bearer"
private const val DATABASE_NAME = "vacancies_database.db"
private const val FILTER_PREFERENCES_NAME = "filter_preferences"

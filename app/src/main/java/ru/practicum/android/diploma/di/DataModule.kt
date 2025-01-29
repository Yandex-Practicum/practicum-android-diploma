package ru.practicum.android.diploma.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.AppDatabase
import ru.practicum.android.diploma.common.constants.APP_DATA_BASE
import ru.practicum.android.diploma.common.constants.APP_SHARED_PREFS
import ru.practicum.android.diploma.common.constants.HH_BASE_URL
import ru.practicum.android.diploma.common.constants.TIMEOUT
import ru.practicum.android.diploma.common.data.Mapper
import ru.practicum.android.diploma.common.data.network.HeadHunterApi
import ru.practicum.android.diploma.common.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.common.util.ConnectivityManager
import ru.practicum.android.diploma.favorites.data.VacancyEntityMapper
import ru.practicum.android.diploma.favorites.data.repository.FavoritesRepositoryImpl
import ru.practicum.android.diploma.favorites.domain.repository.FavoriteRepository
import ru.practicum.android.diploma.filter.data.repository.FilterRepositoryImpl
import ru.practicum.android.diploma.filter.domain.repository.FilterRepository
import ru.practicum.android.diploma.search.data.repository.SearchRepositoryImpl
import ru.practicum.android.diploma.search.domain.repository.SearchRepository
import ru.practicum.android.diploma.vacancy.data.converter.VacancyConverter
import ru.practicum.android.diploma.vacancy.data.impl.VacancyDetailsRepositoryImpl
import ru.practicum.android.diploma.vacancy.domain.api.VacancyDetailsRepository
import java.util.concurrent.TimeUnit

val dataModule = module {

    single {
        androidContext().getSharedPreferences(
            APP_SHARED_PREFS,
            Context.MODE_PRIVATE
        )
    }

    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            APP_DATA_BASE
        ).build()
    }

    single {
        OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    single<HeadHunterApi> {
        Retrofit.Builder()
            .baseUrl(HH_BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HeadHunterApi::class.java)
    }

    factory<Mapper> {
        Mapper()
    }

    single<ConnectivityManager> {
        ConnectivityManager(androidApplication())
    }

    single<RetrofitNetworkClient> {
        RetrofitNetworkClient(get(), get(), get())
    }

    single {
        Gson()
    }

    factory<FavoriteRepository> {
        FavoritesRepositoryImpl(get(), get(), get())
    }

    factory<FilterRepository> {
        FilterRepositoryImpl(get(), get(), get())
    }

    factory<SearchRepository> {
        SearchRepositoryImpl(get(), get())
    }

    single<VacancyDetailsRepository> {
        VacancyDetailsRepositoryImpl(get(), get())
    }
    factory<VacancyConverter> {
        VacancyConverter(androidContext())
    }

    factory {
        VacancyEntityMapper()
    }

}

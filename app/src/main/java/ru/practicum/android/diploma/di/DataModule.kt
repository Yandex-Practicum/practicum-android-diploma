package ru.practicum.android.diploma.di

import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import androidx.room.Room
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.network.token.AccessTokenProvider
import ru.practicum.android.diploma.data.network.token.PrefsAccessTokenProvider
import ru.practicum.android.diploma.data.network.token.TokenInterceptor
import ru.practicum.android.diploma.data.storage.AppPrefsService
import ru.practicum.android.diploma.data.db.FavoriteVacanciesDatabase
import ru.practicum.android.diploma.data.repositories.RepositoryFavoriteVacanciesImpl
import ru.practicum.android.diploma.domain.repositories.RepositoryFavoriteVacancies

val dataModule = module {
    single {
        Room.databaseBuilder(get(), FavoriteVacanciesDatabase::class.java, "FavoriteVacanciesDatabase.db")
            .build()
    }

    single { AppPrefsService(get()) }

    single { PrefsAccessTokenProvider(get()) } bind AccessTokenProvider::class

    single { TokenInterceptor(get()) }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<TokenInterceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(androidContext().getString(R.string.api_link))
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single { get<FavoriteVacanciesDatabase>().getVacancyDao() }

    single<RepositoryFavoriteVacancies> {
        RepositoryFavoriteVacanciesImpl(get())
    }
}

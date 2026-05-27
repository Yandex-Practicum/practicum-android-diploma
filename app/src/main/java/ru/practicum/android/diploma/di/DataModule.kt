package ru.practicum.android.diploma.di

import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.UserDataRepositoryImpl
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.dao.FavoriteVacancyDao
import ru.practicum.android.diploma.data.network.ApiService
import ru.practicum.android.diploma.data.network.AuthInterceptor
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.domain.api.UserDataRepository

val dataModule = module {

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(get()))
            .build()
    }

    single<ApiService> {
        Retrofit.Builder()
            .baseUrl("https://android-diploma.education-services.ru")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    factory { Gson() }

    single<NetworkClient> {
        RetrofitNetworkClient(get(), get())
    }

    single<UserDataRepository> {
        UserDataRepositoryImpl()
    }

    single {
        AppDatabase.buildDatabase(androidContext())
    }

    single<FavoriteVacancyDao> {
        get<AppDatabase>().favoriteVacancyDao()
    }
}

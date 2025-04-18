package ru.practicum.android.diploma.di

import android.app.Application.MODE_PRIVATE
import androidx.room.Room
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.APP_PREFERENCES
import ru.practicum.android.diploma.data.FavVacanciesRepositoryImpl
import ru.practicum.android.diploma.data.IRetrofitApiClient
import ru.practicum.android.diploma.data.StorageRepositoryImpl
import ru.practicum.android.diploma.data.VacancyRepositoryImpl
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.DB_NAME
import ru.practicum.android.diploma.data.mapper.MapperSearchVacancyRequestResponse
import ru.practicum.android.diploma.data.mapper.MapperVacancyDetails
import ru.practicum.android.diploma.data.mapper.VacancyEntityMapper
import ru.practicum.android.diploma.data.network.IApiService
import ru.practicum.android.diploma.data.network.RetrofitApiClient
import ru.practicum.android.diploma.domain.api.IFavVacanciesRepository
import ru.practicum.android.diploma.domain.api.ISharingProvider
import ru.practicum.android.diploma.domain.api.IStorageRepository
import ru.practicum.android.diploma.domain.api.IVacancyRepository
import ru.practicum.android.diploma.domain.impl.SharingProviderImpl

val dataModule = module {

    single<IRetrofitApiClient> {
        RetrofitApiClient(get())
    }

    single<IApiService> {
        Retrofit.Builder()
            .baseUrl("https://api.hh.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IApiService::class.java)
    }

    single {
        androidContext()
            .getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
    }

    single { Gson() }

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
    factory { VacancyEntityMapper() }

    factory { MapperVacancyDetails() }

    factory { MapperSearchVacancyRequestResponse() }

    single<IStorageRepository> {
        StorageRepositoryImpl(storageSharedPreferences = get(), gson = get())
    }

    single<IFavVacanciesRepository> {
        FavVacanciesRepositoryImpl(dataBase = get(), mapper = get())
    }

    single<IVacancyRepository> {
        VacancyRepositoryImpl(
            networkClient = get(),
            filterParam = get(),
            searchMapper = get(),
            vacancyDetailsMapper = get()
        )
    }

    single<ISharingProvider> { params ->
        SharingProviderImpl(params.get())
    }

}

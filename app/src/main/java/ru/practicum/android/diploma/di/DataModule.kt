package ru.practicum.android.diploma.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.converters.CurrencyConverter
import ru.practicum.android.diploma.data.db.converters.ListOfDbConverter
import ru.practicum.android.diploma.data.db.converters.VacancyDtoConverter
import ru.practicum.android.diploma.data.db.converters.VacancyEntityConverter
import ru.practicum.android.diploma.data.network.HeadHunterApi
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.data.repository.CurrencyRepositoryImpl
import ru.practicum.android.diploma.data.repository.DictionaryRepositoryImpl
import ru.practicum.android.diploma.domain.api.dictionary.CurrencyRepository
import ru.practicum.android.diploma.domain.api.dictionary.DictionaryInteractor
import ru.practicum.android.diploma.domain.api.dictionary.DictionaryRepository
import ru.practicum.android.diploma.domain.impl.dictionary.DictionaryInteractorImpl

val dataModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .build()
    }
    single<DictionaryInteractor> { DictionaryInteractorImpl(get(), get()) }
    single<CurrencyRepository> { CurrencyRepositoryImpl(get(), get()) }
    single<DictionaryRepository> { DictionaryRepositoryImpl(get(), get()) }
    factory { VacancyEntityConverter() }
    factory { VacancyDtoConverter() }
    factory { ListOfDbConverter() }
    factory { CurrencyConverter() }
    single<HeadHunterApi> {
        Retrofit.Builder().baseUrl("https://api.hh.ru").addConverterFactory(
            GsonConverterFactory.create()
        ).build().create(HeadHunterApi::class.java)
    }
    single<NetworkClient> { RetrofitNetworkClient(get(), androidContext()) }
}

package ru.practicum.android.diploma.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.database.AppDatabase
import ru.practicum.android.diploma.database.converters.VacancyDbConverter
import ru.practicum.android.diploma.filters.areas.data.dto.converter.ConverterForAreas
import ru.practicum.android.diploma.filters.areas.domain.impl.RegionToAreaConverter
import ru.practicum.android.diploma.search.data.converters.SalaryCurrencySignFormater
import ru.practicum.android.diploma.search.data.converters.SearchVacancyNetworkConverter
import ru.practicum.android.diploma.util.network.ApiKeyInterceptor
import ru.practicum.android.diploma.util.network.HHApiService
import ru.practicum.android.diploma.util.network.NetworkClient
import ru.practicum.android.diploma.util.network.RetrofitNetworkClient
import ru.practicum.android.diploma.vacancy.data.converter.VacancyDetailsNetworkConverter

val dataModule = module {

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        androidContext().getSharedPreferences("setting_preferences", Context.MODE_PRIVATE)
    }

    single { Gson() }

    single<HHApiService> {

        val interceptor = ApiKeyInterceptor(BuildConfig.HH_ACCESS_TOKEN)
        val clientWithInterceptor = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        Retrofit.Builder()
            .baseUrl("https://api.hh.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientWithInterceptor)
            .build()
            .create(HHApiService::class.java)
    }

    single<NetworkClient> {
        RetrofitNetworkClient(androidContext(), get())
    }

    factory<SalaryCurrencySignFormater> {
        SalaryCurrencySignFormater(androidContext())
    }

    factory<SearchVacancyNetworkConverter> {
        SearchVacancyNetworkConverter(get())
    }

    factory<VacancyDetailsNetworkConverter> {
        VacancyDetailsNetworkConverter(androidContext())
    }

    factory {
        VacancyDbConverter()
    }
    factory {
        ConverterForAreas()
    }
    factory {
        RegionToAreaConverter()
    }
}

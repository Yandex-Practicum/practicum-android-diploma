package ru.practicum.android.diploma.di

import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.models.vacancies.VacanciesApi
import ru.practicum.android.diploma.data.models.vacancyDetails.VacancyDetailsApi

val dataModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://api.hh.ru")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // vacanciesApi
    single<VacanciesApi> {
        get<Retrofit>().create(VacanciesApi::class.java)
    }

    // vacancyDetailsApi
    single<VacancyDetailsApi> {
        get<Retrofit>().create(VacancyDetailsApi::class.java)
    }
}

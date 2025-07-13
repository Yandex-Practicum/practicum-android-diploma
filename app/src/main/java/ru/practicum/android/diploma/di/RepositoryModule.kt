package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.sharing.ExternalNavigator
import ru.practicum.android.diploma.data.sharing.impl.ExternalNavigatorImpl
import ru.practicum.android.diploma.data.vacancysearchscreen.impl.VacanciesRepositoryImpl
import ru.practicum.android.diploma.domain.models.api.VacanciesRepository

val repositoryModule = module {
    single<VacanciesRepository> {
        VacanciesRepositoryImpl(get())
    }

    single<ExternalNavigator> {
        ExternalNavigatorImpl(androidContext())
    }
}

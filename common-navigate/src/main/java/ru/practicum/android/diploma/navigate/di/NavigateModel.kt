package ru.practicum.android.diploma.navigate.di

import org.koin.dsl.module
import ru.practicum.android.diploma.navigate.api.VacancyApi
import ru.practicum.android.diploma.navigate.api.impl.VacancyApiImpl
import ru.practicum.android.diploma.navigate.observable.VacancyNavigateLiveData
import ru.practicum.android.diploma.navigate.state.NavigateEventState

val navigateModel = module{
    single {
        VacancyNavigateLiveData()
    }
    single<VacancyApi<NavigateEventState>> { VacancyApiImpl() }
}

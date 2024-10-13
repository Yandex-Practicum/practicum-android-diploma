package ru.practicum.android.diploma.navigate.di

import org.koin.dsl.module
import ru.practicum.android.diploma.navigate.api.NavigateArgsToVacancy
import ru.practicum.android.diploma.navigate.api.impl.NavigateArgsToVacancyImpl
import ru.practicum.android.diploma.navigate.observable.Navigate
import ru.practicum.android.diploma.navigate.observable.impl.NavigateImpl
import ru.practicum.android.diploma.navigate.state.NavigateEventState

val navigateModel = module {

    single<Navigate<NavigateEventState>> {
        NavigateImpl()
    }

    single<NavigateArgsToVacancy<NavigateEventState>> {
        NavigateArgsToVacancyImpl()
    }
}

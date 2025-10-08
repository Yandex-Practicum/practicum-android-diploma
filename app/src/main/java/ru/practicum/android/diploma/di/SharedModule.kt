package ru.practicum.android.diploma.di

import ru.practicum.android.diploma.data.sharing.ExternalNavigator
import org.koin.dsl.module

val sharedModule = module {
    factory<ExternalNavigator> {
        ExternalNavigator(get())
    }
}

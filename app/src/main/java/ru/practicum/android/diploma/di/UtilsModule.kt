package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.api.ExternalNavigator
import ru.practicum.android.diploma.domain.impl.ExternalNavigatorImpl
import ru.practicum.android.diploma.util.NetworkConnectionChecker
import ru.practicum.android.diploma.util.NetworkConnectionCheckerImpl

val utilsModule = module {

    single<NetworkConnectionChecker> {
        NetworkConnectionCheckerImpl(get())
    }

    single<ExternalNavigator> {
        ExternalNavigatorImpl(get())
    }
}

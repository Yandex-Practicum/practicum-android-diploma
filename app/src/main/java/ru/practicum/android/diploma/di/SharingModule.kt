package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.sharing.ExternalNavigatorImpl
import ru.practicum.android.diploma.domain.api.sharing.ExternalNavigator
import ru.practicum.android.diploma.domain.api.sharing.SharingInteractor
import ru.practicum.android.diploma.domain.impl.sharing.SharingInteractorImpl

val sharingModule = module {
    single<ExternalNavigator> { ExternalNavigatorImpl(get()) }
    single<SharingInteractor> { SharingInteractorImpl(get()) }
}

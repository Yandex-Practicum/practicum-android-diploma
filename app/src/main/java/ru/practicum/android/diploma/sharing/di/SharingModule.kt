package ru.practicum.android.diploma.sharing.di

import dagger.Binds
import dagger.Module
import ru.practicum.android.diploma.sharing.data.ExternalNavigatorImpl
import ru.practicum.android.diploma.sharing.domain.api.ExternalNavigator
import ru.practicum.android.diploma.sharing.domain.api.SharingInteractor
import ru.practicum.android.diploma.sharing.domain.impl.SharingInteractorImpl

@Module
abstract class SharingModule {
    
    @Binds
    abstract fun bindExternalNavigator(externalNavigatorImpl: ExternalNavigatorImpl): ExternalNavigator

    @Binds
    abstract fun bindSharingInteractor(sharingInteractorImpl: SharingInteractorImpl): SharingInteractor
}
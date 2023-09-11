package ru.practicum.android.diploma.details.di

import dagger.Binds
import dagger.Module
import ru.practicum.android.diploma.details.domain.DetailsInteractor
import ru.practicum.android.diploma.details.domain.DetailsLocalInteractor
import ru.practicum.android.diploma.details.domain.impl.DetailsInteractorImpl
import ru.practicum.android.diploma.details.domain.impl.DetailsLocalInteractorImpl

@Module
interface DetailsDomainModule {
    @Binds
    fun bindDetailsInteractor(detailsInteractorImpl: DetailsInteractorImpl): DetailsInteractor

    @Binds
    fun bindDetailsLocalInteractor(detailsLocalInteractorImpl: DetailsLocalInteractorImpl): DetailsLocalInteractor
}
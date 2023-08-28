package ru.practicum.android.diploma.details.di

import dagger.Binds
import dagger.Module
import ru.practicum.android.diploma.details.domain.DetailsInteractor
import ru.practicum.android.diploma.details.domain.impl.DetailsInteractorImpl

@Module
interface DetailsDomainModule {
    @Binds
    fun bindDetailsInteractor(detailsInteractorImpl: DetailsInteractorImpl): DetailsInteractor
}
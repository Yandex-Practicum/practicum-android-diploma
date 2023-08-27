package ru.practicum.android.diploma.details.di

import dagger.Binds
import dagger.Module
import ru.practicum.android.diploma.details.data.DetailsRepositoryImpl
import ru.practicum.android.diploma.details.domain.DetailsRepository

@Module
interface DetailsDataModule {
    @Binds
    fun bindDetailsRepository(detailsRepositoryImpl: DetailsRepositoryImpl): DetailsRepository
}
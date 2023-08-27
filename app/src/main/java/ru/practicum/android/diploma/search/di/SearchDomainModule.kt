package ru.practicum.android.diploma.search.di

import dagger.Binds
import dagger.Module
import ru.practicum.android.diploma.search.data.RemoteRepositoryImpl
import ru.practicum.android.diploma.search.domain.RemoteRepository
import ru.practicum.android.diploma.search.domain.SearchVacanciesUseCase
import ru.practicum.android.diploma.search.domain.impl.SearchVacanciesUseCaseImpl

@Module
interface SearchDomainModule {
    @Binds
    fun bindRemoteRepository(remoteRepositoryImpl: RemoteRepositoryImpl): RemoteRepository
    @Binds
    fun bindSearchVacanciesUseCase(searchVacanciesUseCaseImpl: SearchVacanciesUseCaseImpl): SearchVacanciesUseCase
}
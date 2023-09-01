package ru.practicum.android.diploma.search.di

import dagger.Binds
import dagger.Module
import ru.practicum.android.diploma.search.data.SearchRepositoryImpl
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.search.domain.api.SearchVacanciesUseCase
import ru.practicum.android.diploma.search.domain.impl.SearchVacanciesUseCaseImpl

@Module
interface SearchDomainModule {
    @Binds
    fun bindSearchRepository(remoteRepositoryImpl: SearchRepositoryImpl): SearchRepository
    @Binds
    fun bindSearchVacanciesUseCase(searchVacanciesUseCaseImpl: SearchVacanciesUseCaseImpl): SearchVacanciesUseCase
}
package ru.practicum.android.diploma.filter.di

import dagger.Binds
import dagger.Module
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.api.GetAllCountriesUseCase
import ru.practicum.android.diploma.filter.domain.impl.FilterInteractorImpl
import ru.practicum.android.diploma.filter.domain.impl.GetAllCountriesUseCaseImpl

@Module
interface FilterDomainModule {
    @Binds
    fun bindFilterInteractor(filterInteractorImpl: FilterInteractorImpl): FilterInteractor


    @Binds
    fun bindGetAllCountriesUseCase(getAllCountriesUseCaseImpl: GetAllCountriesUseCaseImpl): GetAllCountriesUseCase
}
package ru.practicum.android.diploma.filter.di

import dagger.Binds
import dagger.Module
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.api.GetAllCountriesUseCase
import ru.practicum.android.diploma.filter.domain.api.GetIndustriesUseCase
import ru.practicum.android.diploma.filter.domain.api.GetRegionInteractor
import ru.practicum.android.diploma.filter.domain.impl.FilterInteractorImpl
import ru.practicum.android.diploma.filter.domain.impl.GetAllCountriesUseCaseImpl
import ru.practicum.android.diploma.filter.domain.impl.GetIndustriesUseCaseImpl
import ru.practicum.android.diploma.filter.domain.impl.GetRegionInteractorImpl

@Module
interface FilterDomainModule {
    @Binds
    fun bindFilterInteractor(filterInteractorImpl: FilterInteractorImpl): FilterInteractor

    @Binds
    fun bindGetAllCountriesUseCase(getAllCountriesUseCaseImpl: GetAllCountriesUseCaseImpl): GetAllCountriesUseCase

    @Binds
    fun bindGetIndustriesUseCase(getIndustriesUseCaseImpl: GetIndustriesUseCaseImpl) : GetIndustriesUseCase

    @Binds
    fun bindGetRegionUseCase(getRegionInteractor: GetRegionInteractorImpl) : GetRegionInteractor
}
package ru.practicum.android.diploma.filter.di

import dagger.Binds
import dagger.Module
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.api.GetAllCountriesUseCase
import ru.practicum.android.diploma.filter.domain.api.GetIndustriesUseCase
import ru.practicum.android.diploma.filter.domain.api.GetRegionUseCase
import ru.practicum.android.diploma.filter.domain.impl.FilterInteractorImpl
import ru.practicum.android.diploma.filter.domain.impl.GetAllCountriesUseCaseImpl
import ru.practicum.android.diploma.filter.domain.impl.GetIndustriesUseCaseImpl
import ru.practicum.android.diploma.filter.domain.impl.GetRegionUseCaseImpl

@Module
interface FilterDomainModule {
    @Binds
    fun bindFilterInteractor(filterInteractorImpl: FilterInteractorImpl): FilterInteractor

    @Binds
    fun bindGetAllCountriesUseCase(getAllCountriesUseCaseImpl: GetAllCountriesUseCaseImpl): GetAllCountriesUseCase

    @Binds
    fun bindGetIndustriesUseCase(getIndustriesUseCaseImpl: GetIndustriesUseCaseImpl) : GetIndustriesUseCase

    @Binds
    fun bindGetRegionUseCase(getRegionInteractor: GetRegionUseCaseImpl) : GetRegionUseCase
}
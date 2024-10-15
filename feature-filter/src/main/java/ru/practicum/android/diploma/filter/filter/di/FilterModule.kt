package ru.practicum.android.diploma.filter.filter.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.practicum.android.diploma.filter.filter.data.repositoryimpl.FilterSPRepositoryImpl
import ru.practicum.android.diploma.filter.filter.domain.repository.FilterSPRepository
import ru.practicum.android.diploma.filter.filter.domain.usecase.impl.FilterSPInteractor
import ru.practicum.android.diploma.filter.filter.domain.usecase.impl.FilterSPInteractorImpl
import ru.practicum.android.diploma.filter.filter.presentation.viewmodel.FilterViewModel
import ru.practicum.android.diploma.filter.industry.domain.repository.IndustryRepository
import ru.practicum.android.diploma.filter.industry.domain.usecase.IndustryInteractor

val filterModule = module {
    singleOf(_root_ide_package_.ru.practicum.android.diploma.filter.industry.domain.usecase.impl::IndustryInteractorImpl) bind IndustryInteractor::class
    singleOf(_root_ide_package_.ru.practicum.android.diploma.filter.industry.data.repositoryimpl::IndustryRepositoryImpl) bind IndustryRepository::class
    singleOf(_root_ide_package_.ru.practicum.android.diploma.filter.industry.data.mappers::IndustryMapper)
//todo finish
    viewModelOf(_root_ide_package_.ru.practicum.android.diploma.filter.industry.presentation.viewmodel::IndustryViewModel)
    single<FilterSPInteractor> {
        FilterSPInteractorImpl(get())
    }
    singleOf(::FilterSPRepositoryImpl) bind FilterSPRepository::class
    singleOf(::FilterSPInteractorImpl) bind FilterSPInteractor::class

    viewModelOf(::FilterViewModel)
}

package ru.practicum.android.diploma.filter.industry.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.practicum.android.diploma.filter.industry.data.mappers.IndustryMapper
import ru.practicum.android.diploma.filter.industry.data.repositoryimpl.IndustryRepositoryImpl
import ru.practicum.android.diploma.filter.industry.domain.repository.IndustryRepository
import ru.practicum.android.diploma.filter.industry.domain.usecase.IndustryInteractor
import ru.practicum.android.diploma.filter.industry.domain.usecase.impl.IndustryInteractorImpl
import ru.practicum.android.diploma.filter.industry.presentation.viewmodel.IndustryViewModel

val industryModule = module {

    singleOf(::IndustryInteractorImpl) bind IndustryInteractor::class
    singleOf(::IndustryRepositoryImpl) bind IndustryRepository::class
    singleOf(::IndustryMapper)

    viewModelOf(::IndustryViewModel)
}

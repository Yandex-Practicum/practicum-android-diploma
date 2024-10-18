package ru.practicum.android.diploma.filter.industry.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.practicum.android.diploma.filter.industry.data.repositoryimpl.network.IndustryRepositoryNetworkImpl
import ru.practicum.android.diploma.filter.industry.data.repositoryimpl.sp.IndustryRepositorySpImpl
import ru.practicum.android.diploma.filter.industry.domain.repository.IndustryRepositoryNetwork
import ru.practicum.android.diploma.filter.industry.domain.repository.IndustryRepositorySp
import ru.practicum.android.diploma.filter.industry.domain.usecase.IndustryInteractor
import ru.practicum.android.diploma.filter.industry.domain.usecase.impl.IndustryInteractorImpl
import ru.practicum.android.diploma.filter.industry.presentation.viewmodel.IndustryViewModel

val industryModule = module {

    singleOf(::IndustryInteractorImpl) bind IndustryInteractor::class
    singleOf(::IndustryRepositoryNetworkImpl) bind IndustryRepositoryNetwork::class
    singleOf(::IndustryRepositorySpImpl) bind IndustryRepositorySp::class

    viewModelOf(::IndustryViewModel)
}

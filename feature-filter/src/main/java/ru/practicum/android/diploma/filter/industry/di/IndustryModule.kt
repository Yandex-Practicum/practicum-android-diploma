package ru.practicum.android.diploma.filter.industry.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
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
    viewModelOf(::IndustryViewModel)

    factory { IndustryInteractorImpl(get(), get()) } bind IndustryInteractor::class
    factory { IndustryRepositoryNetworkImpl(get(), get()) } bind IndustryRepositoryNetwork::class
    factory { IndustryRepositorySpImpl(get()) } bind IndustryRepositorySp::class

}

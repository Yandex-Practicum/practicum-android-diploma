package ru.practicum.android.diploma.filter.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.filter.data.repositoryimpl.network.RegionRepositoryImpl
import ru.practicum.android.diploma.filter.domain.repository.RegionRepository
import ru.practicum.android.diploma.filter.domain.usecase.RegionInteractor
import ru.practicum.android.diploma.filter.domain.usecase.impl.RegionInteractorImpl
import ru.practicum.android.diploma.filter.util.AreaConverter

val filterModule = module {
    single<RegionInteractor> {
        RegionInteractorImpl(get())
    }

    single<RegionRepository> {
        RegionRepositoryImpl(get(), get(), androidContext())
    }

    factory {
        AreaConverter()
    }
}

package ru.practicum.android.diploma.filter.place.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.filter.place.data.mappers.AreaMapper
import ru.practicum.android.diploma.filter.place.data.repositoryimpl.network.RegionRepositoryImpl
import ru.practicum.android.diploma.filter.place.domain.repository.RegionRepository
import ru.practicum.android.diploma.filter.place.domain.usecase.RegionInteractor
import ru.practicum.android.diploma.filter.place.domain.usecase.impl.RegionInteractorImpl
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.RegionsCountriesViewModel

val placeModule = module {
    single<RegionInteractor> {
        RegionInteractorImpl(get())
    }

    single<RegionRepository> {
        RegionRepositoryImpl(
            get(),
            get(),
            androidContext()
        )
    }

    factory {
        AreaMapper()
    }

    viewModel {
        RegionsCountriesViewModel(get())
    }
}

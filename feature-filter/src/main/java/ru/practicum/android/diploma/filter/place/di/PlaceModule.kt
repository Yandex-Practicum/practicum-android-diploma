package ru.practicum.android.diploma.filter.place.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.filter.place.data.repositoryimpl.network.RegionNetworkRepositoryImpl
import ru.practicum.android.diploma.filter.place.data.repositoryimpl.sp.RegionSpRepositoryImpl
import ru.practicum.android.diploma.filter.place.domain.repository.RegionNetworkRepository
import ru.practicum.android.diploma.filter.place.domain.repository.RegionSpRepository
import ru.practicum.android.diploma.filter.place.domain.usecase.RegionInteractor
import ru.practicum.android.diploma.filter.place.domain.usecase.impl.RegionInteractorImpl
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.RegionsCountriesViewModel

val placeModule = module {
    single<RegionInteractor> {
        RegionInteractorImpl(
            get(),
            get()
        )
    }

    single<RegionSpRepository> {
        RegionSpRepositoryImpl(
            get()
        )
    }

    single<RegionNetworkRepository> {
        RegionNetworkRepositoryImpl(
            get(),
            androidContext()
        )
    }

    viewModel {
        RegionsCountriesViewModel(get())
    }
}

package ru.practicum.android.diploma.filter.place.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.practicum.android.diploma.filter.place.data.repositoryimpl.cache.CacheRepositoryImpl
import ru.practicum.android.diploma.filter.place.data.repositoryimpl.network.NetworkRepositoryImpl
import ru.practicum.android.diploma.filter.place.data.repositoryimpl.sp.SpRepositoryImpl
import ru.practicum.android.diploma.filter.place.domain.repository.CacheRepository
import ru.practicum.android.diploma.filter.place.domain.repository.NetworkRepository
import ru.practicum.android.diploma.filter.place.domain.repository.SpRepository
import ru.practicum.android.diploma.filter.place.domain.usecase.RegionInteractor
import ru.practicum.android.diploma.filter.place.domain.usecase.impl.RegionInteractorImpl
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.CountryViewModel
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.PlaceViewModel
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.RegionViewModel

val placeModule = module {
    viewModelOf(::PlaceViewModel)
    viewModelOf(::CountryViewModel)
    viewModelOf(::RegionViewModel)

    factory<RegionInteractor> {
        RegionInteractorImpl(
            networkRepository = get(),
            spRepository = get(),
            cacheRepository = get()
        )
    }

    factory<CacheRepository> {
        CacheRepositoryImpl(
            get()
        )
    }

    factory<SpRepository> {
        SpRepositoryImpl(
            get()
        )
    }

    factory<NetworkRepository> {
        NetworkRepositoryImpl(
            get(),
            androidContext()
        )
    }
}

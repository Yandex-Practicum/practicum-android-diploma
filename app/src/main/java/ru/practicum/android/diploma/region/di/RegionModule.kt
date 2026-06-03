package ru.practicum.android.diploma.region.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.region.data.RegionRepositoryImpl
import ru.practicum.android.diploma.region.domain.api.RegionInteractor
import ru.practicum.android.diploma.region.domain.api.RegionRepository
import ru.practicum.android.diploma.region.domain.impl.RegionInteractorImpl
import ru.practicum.android.diploma.region.ui.RegionViewModel
import ru.practicum.android.diploma.region.ui.RegionViewModelImpl

val regionModule = module {
    single<RegionRepository> {
        RegionRepositoryImpl(get())
    }
    factory<RegionInteractor> {
        RegionInteractorImpl(get())
    }
    viewModel<RegionViewModel> { (countryId: String?) ->
        RegionViewModelImpl(get(), countryId)
    }
}

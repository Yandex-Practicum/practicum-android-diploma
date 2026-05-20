package ru.practicum.android.diploma.region.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.region.ui.RegionViewModel
import ru.practicum.android.diploma.region.ui.RegionViewModelImpl

val regionModule = module {
    viewModel<RegionViewModel> {
        RegionViewModelImpl()
    }
}

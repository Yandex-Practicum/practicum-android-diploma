package ru.practicum.android.diploma.area.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.area.ui.AreaViewModel
import ru.practicum.android.diploma.area.ui.AreaViewModelImpl

val areaModule = module {
    viewModel<AreaViewModel> {
        AreaViewModelImpl()
    }
}

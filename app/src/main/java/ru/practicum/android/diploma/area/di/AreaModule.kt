package ru.practicum.android.diploma.area.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.area.domain.api.AreaInteractor
import ru.practicum.android.diploma.area.domain.impl.AreaInteractorImpl
import ru.practicum.android.diploma.area.ui.AreaViewModel
import ru.practicum.android.diploma.area.ui.AreaViewModelImpl

val areaModule = module {
    factory<AreaInteractor> {
        AreaInteractorImpl(get())
    }
    viewModel<AreaViewModel> {
        AreaViewModelImpl(get())
    }
}

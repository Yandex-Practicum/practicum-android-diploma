package ru.practicum.android.diploma.industry.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.industry.data.IndustriesRepositoryImpl
import ru.practicum.android.diploma.industry.domain.api.IndustriesInteractor
import ru.practicum.android.diploma.industry.domain.api.IndustriesRepository
import ru.practicum.android.diploma.industry.domain.impl.IndustriesInteractorImpl
import ru.practicum.android.diploma.industry.ui.IndustryViewModel
import ru.practicum.android.diploma.industry.ui.IndustryViewModelImpl

val industryModule = module {

    single<IndustriesRepository> {
        IndustriesRepositoryImpl(get())
    }
    single<IndustriesInteractor> {
        IndustriesInteractorImpl(get(), get())
    }
    viewModel<IndustryViewModel> {
        IndustryViewModelImpl(get())
    }
}

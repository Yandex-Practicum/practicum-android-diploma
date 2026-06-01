package ru.practicum.android.diploma.country.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.country.domain.api.CountryInteractor
import ru.practicum.android.diploma.country.domain.impl.CountryInteractorImpl
import ru.practicum.android.diploma.country.ui.CountryViewModel
import ru.practicum.android.diploma.country.ui.CountryViewModelImpl

val countryModule = module {
    factory<CountryInteractor> {
        CountryInteractorImpl(get(), get())
    }
    viewModel<CountryViewModel> {
        CountryViewModelImpl(get())
    }
}

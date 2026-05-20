package ru.practicum.android.diploma.country.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.country.ui.CountryViewModel
import ru.practicum.android.diploma.country.ui.CountryViewModelImpl

val countryModule = module {
    viewModel<CountryViewModel> {
        CountryViewModelImpl()
    }
}

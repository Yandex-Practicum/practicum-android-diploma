package ru.practicum.android.diploma.industry.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.industry.ui.IndustryViewModel
import ru.practicum.android.diploma.industry.ui.IndustryViewModelImpl

val industryModule = module {
    viewModel<IndustryViewModel> {
        IndustryViewModelImpl()
    }
}

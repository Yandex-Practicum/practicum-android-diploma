package ru.practicum.android.diploma.vacancy.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.vacancy.ui.VacancyViewModel
import ru.practicum.android.diploma.vacancy.ui.VacancyViewModelImpl

val vacancyModule = module {
    viewModel<VacancyViewModel> { params ->
        VacancyViewModelImpl(params.get())
    }
}

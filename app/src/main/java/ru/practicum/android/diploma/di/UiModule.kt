package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import org.koin.dsl.bind
import ru.practicum.android.diploma.features.vacancydetails.presentation.VacancyDetailsViewModel
import ru.practicum.android.diploma.features.search.presentation.viewModel.SearchViewModel


val uiModule = module {

    viewModel<VacancyDetailsViewModel>{
        VacancyDetailsViewModel()
    }
    
    viewModelOf(::SearchViewModel).bind()
}
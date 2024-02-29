package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.practicum.android.diploma.ui.filter.ChooseIndustryViewModel

val industriesModule = module {
    viewModelOf(::ChooseIndustryViewModel)
}

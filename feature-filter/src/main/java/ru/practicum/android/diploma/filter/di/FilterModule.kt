package ru.practicum.android.diploma.filter.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.practicum.android.diploma.filter.presentation.viewmodel.ProfessionViewModel

val filterModule = module {
    viewModelOf(::ProfessionViewModel)
}

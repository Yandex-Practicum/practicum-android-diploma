package ru.practicum.android.diploma.filter.profession.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.practicum.android.diploma.filter.profession.presentation.viewmodel.ProfessionViewModel

val professionModule = module {
    viewModelOf(::ProfessionViewModel)
}

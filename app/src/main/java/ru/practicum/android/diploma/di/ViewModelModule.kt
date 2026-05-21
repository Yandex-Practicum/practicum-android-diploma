package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.search.viewmodel.JobSearchViewModel

val viewModelModule = module {
    factory {
        JobSearchViewModel(get())
    }
}

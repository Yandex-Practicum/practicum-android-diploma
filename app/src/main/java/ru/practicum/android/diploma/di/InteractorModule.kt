package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.impl.SearchInteractor

val interactorModule = module {
    factory {
        SearchInteractor(get())
    }
}

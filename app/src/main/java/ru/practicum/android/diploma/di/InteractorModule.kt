package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.search.domain.usecase.SearchVacancyUseCase

val interactorModule = module {
    single {
        SearchVacancyUseCase(searchVacancyRepository = get())
    }
}

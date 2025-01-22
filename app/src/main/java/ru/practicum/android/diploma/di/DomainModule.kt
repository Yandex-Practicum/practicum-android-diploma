package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.search.usecase.GetVacanciesUseCase

val domainModule = module {
    factory<GetVacanciesUseCase> {
        GetVacanciesUseCase(get())
    }
}

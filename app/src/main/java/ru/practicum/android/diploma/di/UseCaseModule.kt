package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.usecase.GetVacanciesUseCase

val useCaseModule = module {
    factory { GetVacanciesUseCase(repository = get()) }
}

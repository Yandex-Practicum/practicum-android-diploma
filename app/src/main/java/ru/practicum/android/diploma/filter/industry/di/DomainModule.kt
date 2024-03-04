package ru.practicum.android.diploma.filter.industry.di

import org.koin.dsl.module
import ru.practicum.android.diploma.filter.industry.domain.usecase.GetIndustriesByTextUseCase

val industryDomainModule = module {
    factory {
        GetIndustriesByTextUseCase(
            industryRepository = get()
        )
    }
}

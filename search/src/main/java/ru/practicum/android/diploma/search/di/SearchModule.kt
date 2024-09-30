package ru.practicum.android.diploma.search.di

import org.koin.dsl.module
import ru.practicum.android.diploma.search.data.repositoryimpl.network.VacanciesRepositoryImpl
import ru.practicum.android.diploma.search.domain.repository.VacanciesRepository
import ru.practicum.android.diploma.search.domain.usecase.VacanciesInteractor
import ru.practicum.android.diploma.search.domain.usecase.impl.VacanciesInteractorImpl
import ru.practicum.android.diploma.search.util.AreaConverter
import ru.practicum.android.diploma.search.util.IndustryConverter
import ru.practicum.android.diploma.search.util.VacancyConverter

val searchModule = module {
    single {
        AreaConverter()
    }

    single {
        IndustryConverter()
    }

    single {
        VacancyConverter()
    }

    single<VacanciesInteractor> {
        VacanciesInteractorImpl(get())
    }

    single<VacanciesRepository> {
        VacanciesRepositoryImpl(get(), get(), get(), get())
    }
}

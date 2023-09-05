package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.practicum.android.diploma.search.domain.SearchInteractor
import ru.practicum.android.diploma.search.domain.SearchInteractorImpl
import ru.practicum.android.diploma.db.domain.api.VacancyDbInteractor
import ru.practicum.android.diploma.db.domain.impl.VacancyDbInteractorImpl
import ru.practicum.android.diploma.details.domain.VacancyInteractor
import ru.practicum.android.diploma.details.domain.VacancyInteractorImpl

val interactorModule = module {
    singleOf(::SearchInteractorImpl).bind<SearchInteractor>()
    single<VacancyDbInteractor> {
        VacancyDbInteractorImpl(
            vacancyDbConverter = get(),
            vacancyDbRepository = get()
        )
    }

    factory<VacancyInteractor>{
        VacancyInteractorImpl(get())
    }
}
package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.mappers.VacancyResponseToDomainMapper
import ru.practicum.android.diploma.domain.api.FavoritesVacancyInteractor
import ru.practicum.android.diploma.domain.api.SearchInteractor
import ru.practicum.android.diploma.domain.impl.FavoritesVacancyInteractorImpl
import ru.practicum.android.diploma.domain.impl.SearchInteractorImpl
import ru.practicum.android.diploma.domain.interactors.GetVacancyDetailsInteractor

val interactorModule = module {

    factory<FavoritesVacancyInteractor> {
        FavoritesVacancyInteractorImpl()
    }

    factory<SearchInteractor> {
        SearchInteractorImpl(get())
    }

    factory<GetVacancyDetailsInteractor> {
        GetVacancyDetailsInteractor(get())
    }

    factory {
        VacancyResponseToDomainMapper()
    }

}

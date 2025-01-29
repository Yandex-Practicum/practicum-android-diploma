package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.favorites.api.FavoritesInteractor
import ru.practicum.android.diploma.domain.favorites.impl.FavoritesInteractorImpl
import ru.practicum.android.diploma.domain.search.api.VacanciesInteractor
import ru.practicum.android.diploma.domain.search.impl.VacanciesInteractorImpl
import ru.practicum.android.diploma.domain.sharing.api.ShareVacancyUseCase
import ru.practicum.android.diploma.domain.sharing.impl.ShareVacancyUseCaseImpl
import ru.practicum.android.diploma.domain.vacancydetails.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.vacancydetails.impl.VacancyDetailsInteractorImpl

val domainModule = module {
    factory<VacanciesInteractor> {
        VacanciesInteractorImpl(get())
    }

    factory<VacancyDetailsInteractor> {
        VacancyDetailsInteractorImpl(get())
    }

    factory<FavoritesInteractor> {
        FavoritesInteractorImpl(get())
    }

    factory<ShareVacancyUseCase> {
        ShareVacancyUseCaseImpl(get())
    }
}

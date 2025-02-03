package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.areas.api.AreasInteractor
import ru.practicum.android.diploma.domain.areas.impl.AreasInteractorImpl
import ru.practicum.android.diploma.domain.favorites.api.FavoritesInteractor
import ru.practicum.android.diploma.domain.favorites.impl.FavoritesInteractorImpl
import ru.practicum.android.diploma.domain.filter.api.FilterInteractor
import ru.practicum.android.diploma.domain.filter.impl.FilterInteractorImpl
import ru.practicum.android.diploma.domain.industries.api.IndustriesInteractor
import ru.practicum.android.diploma.domain.industries.impl.IndustriesInteractorImpl
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

    factory<AreasInteractor> {
        AreasInteractorImpl(get())
    }

    factory<IndustriesInteractor> {
        IndustriesInteractorImpl(get())
    }

    factory<FilterInteractor> {
        FilterInteractorImpl(get())
    }
}

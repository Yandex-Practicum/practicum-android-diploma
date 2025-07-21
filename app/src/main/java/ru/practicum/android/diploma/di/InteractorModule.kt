package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.favouritevacancies.impl.FavouriteVacanciesDbInteractorImpl
import ru.practicum.android.diploma.domain.favouritevacancies.repository.FavouriteVacanciesDbRepository
import ru.practicum.android.diploma.domain.favouritevacancies.usecases.FavouriteVacanciesDbInteractor
import ru.practicum.android.diploma.domain.filters.impl.FiltersInteractorImpl
import ru.practicum.android.diploma.domain.filters.impl.FiltersParametersInteractorImpl
import ru.practicum.android.diploma.domain.filters.repository.FiltersInteractor
import ru.practicum.android.diploma.domain.filters.repository.FiltersParametersInteractor
import ru.practicum.android.diploma.domain.models.api.VacanciesInteractor
import ru.practicum.android.diploma.domain.sharing.SharingInteractor
import ru.practicum.android.diploma.domain.sharing.impl.SharingInteractorImpl
import ru.practicum.android.diploma.domain.vacancysearchscreen.impl.VacanciesInteractorImpl

val interactorModule = module {
    // FavouriteVacancies
    factory<FavouriteVacanciesDbInteractor> {
        FavouriteVacanciesDbInteractorImpl(get<FavouriteVacanciesDbRepository>())
    }
    single<VacanciesInteractor> {
        VacanciesInteractorImpl(get())
    }

    single<SharingInteractor> {
        SharingInteractorImpl(get())
    }

    single<FiltersInteractor> {
        FiltersInteractorImpl(get())
    }

    single<FiltersParametersInteractor> {
        FiltersParametersInteractorImpl(get())
    }
}

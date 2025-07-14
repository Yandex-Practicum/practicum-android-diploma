package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.models.api.VacanciesInteractor
import ru.practicum.android.diploma.domain.sharing.SharingInteractor
import ru.practicum.android.diploma.domain.sharing.impl.SharingInteractorImpl
import ru.practicum.android.diploma.domain.vacancysearchscreen.impl.VacanciesInteractorImpl
import ru.practicum.android.diploma.domain.favouritevacancies.impl.FavouriteVacanciesDbInteractorImpl
import ru.practicum.android.diploma.domain.favouritevacancies.repository.FavouriteVacanciesDbRepository
import ru.practicum.android.diploma.domain.favouritevacancies.usecases.FavouriteVacanciesDbInteractor

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
}

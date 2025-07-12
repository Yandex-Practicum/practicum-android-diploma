package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.favouriteVacancies.impl.FavouriteVacanciesDbInteractorImpl
import ru.practicum.android.diploma.domain.favouriteVacancies.repository.FavouriteVacanciesDbRepository
import ru.practicum.android.diploma.domain.favouriteVacancies.use_cases.FavouriteVacanciesDbInteractor
import ru.practicum.android.diploma.domain.vacancysearchscreen.impl.VacanciesInteractorImpl
import ru.practicum.android.diploma.domain.models.api.VacanciesInteractor

val interactorModule = module {
    // FavouriteVacancies
    factory<FavouriteVacanciesDbInteractor> {
        FavouriteVacanciesDbInteractorImpl(get<FavouriteVacanciesDbRepository>())
    }
    single<VacanciesInteractor> {
        VacanciesInteractorImpl(get())
    }
}

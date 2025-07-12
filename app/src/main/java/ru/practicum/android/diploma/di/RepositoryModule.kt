package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.favouritevacancies.impl.FavouriteVacanciesDbRepositoryImpl
import ru.practicum.android.diploma.domain.favouriteVacancies.repository.FavouriteVacanciesDbRepository
import ru.practicum.android.diploma.data.vacancysearchscreen.impl.VacanciesRepositoryImpl
import ru.practicum.android.diploma.domain.models.api.VacanciesRepository

val repositoryModule = module {
    // FavouriteVacancies
    single<FavouriteVacanciesDbRepository> {
        FavouriteVacanciesDbRepositoryImpl(get<AppDatabase>())
    }
    single<VacanciesRepository> {
        VacanciesRepositoryImpl(get())
    }
}

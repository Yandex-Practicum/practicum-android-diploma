package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.favouritevacancies.impl.FavouriteVacanciesDbRepositoryImpl
import ru.practicum.android.diploma.data.filters.impl.FiltersParametersRepositoryImpl
import ru.practicum.android.diploma.data.filters.impl.FiltersRepositoryImpl
import ru.practicum.android.diploma.data.filters.storage.api.FilterParametersStorage
import ru.practicum.android.diploma.data.filters.storage.impl.FilterParametersStorageImpl
import ru.practicum.android.diploma.data.sharing.ExternalNavigator
import ru.practicum.android.diploma.data.sharing.impl.ExternalNavigatorImpl
import ru.practicum.android.diploma.data.vacancysearchscreen.impl.VacanciesRepositoryImpl
import ru.practicum.android.diploma.domain.favouritevacancies.repository.FavouriteVacanciesDbRepository
import ru.practicum.android.diploma.domain.filters.repository.FilterParametersRepository
import ru.practicum.android.diploma.domain.filters.repository.FiltersRepository
import ru.practicum.android.diploma.domain.models.api.VacanciesRepository

val repositoryModule = module {
    // FavouriteVacancies
    single<FavouriteVacanciesDbRepository> {
        FavouriteVacanciesDbRepositoryImpl(get<AppDatabase>())
    }
    single<VacanciesRepository> {
        VacanciesRepositoryImpl(get())
    }

    single<ExternalNavigator> {
        ExternalNavigatorImpl(androidContext())
    }

    single<FiltersRepository> {
        FiltersRepositoryImpl(get())
    }

    single<FilterParametersStorage> {
        FilterParametersStorageImpl(get(), get(named("filter_parameters_prefs")))
    }

    single<FilterParametersRepository> {
        FiltersParametersRepositoryImpl(get())
    }
}

package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.favourites.data.AddToFavouritesRepositoryImpl
import ru.practicum.android.diploma.favourites.data.GetFavouritesRepositoryImpl
import ru.practicum.android.diploma.favourites.domain.api.AddToFavouritesRepository
import ru.practicum.android.diploma.favourites.domain.api.GetFavouritesRepository
import ru.practicum.android.diploma.filter.area.data.AreaRepositoryImpl
import ru.practicum.android.diploma.filter.area.domain.api.AreaRepository
import ru.practicum.android.diploma.search.data.SearchVacancyRepositoryImpl
import ru.practicum.android.diploma.search.domain.api.SearchVacancyRepository
import ru.practicum.android.diploma.vacancy.data.DetailVacancyRepositoryImpl
import ru.practicum.android.diploma.vacancy.domain.api.DetailVacancyRepository

val repositoryModule = module {
    single<DetailVacancyRepository> {
        DetailVacancyRepositoryImpl(database = get(), networkClient = get())
    }
    single<SearchVacancyRepository> {
        SearchVacancyRepositoryImpl(networkClient = get())
    }
    single<GetFavouritesRepository> {
        GetFavouritesRepositoryImpl(appDatabase = get())
    }
    single<AddToFavouritesRepository> {
        AddToFavouritesRepositoryImpl(appDatabase = get())
    }

    single<AreaRepository> {
        AreaRepositoryImpl(networkClient = get())
    }


}

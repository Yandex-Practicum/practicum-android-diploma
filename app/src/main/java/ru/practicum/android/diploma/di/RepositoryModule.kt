package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.favorite.impl.FavoriteRepositoryImpl
import ru.practicum.android.diploma.data.vacancylist.SearchRepositoryImpl
import ru.practicum.android.diploma.domain.favorite.FavoriteRepository
import ru.practicum.android.diploma.domain.main.SearchRepository

val repositoryModule = module {

    single<SearchRepository> {
        SearchRepositoryImpl(get())
    }

    single<FavoriteRepository> {
        FavoriteRepositoryImpl(get())
    }
}

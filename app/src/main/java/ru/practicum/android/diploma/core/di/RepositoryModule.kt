package ru.practicum.android.diploma.core.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.FavouriteRepositoryImpl
import ru.practicum.android.diploma.data.SearchRepositoryImpl
import ru.practicum.android.diploma.domain.api.SearchRepository
import ru.practicum.android.diploma.data.detail.DetailRepositoryImpl
import ru.practicum.android.diploma.data.detail.Mapper
import ru.practicum.android.diploma.domain.detail.DetailRepository
import ru.practicum.android.diploma.domain.favorite.FavouriteRepository

val repositoryModule = module {
    single<DetailRepository> { DetailRepositoryImpl(get()) }
    single<FavouriteRepository> { FavouriteRepositoryImpl(get(),get()) }


    single { Mapper() }

    single<SearchRepository> { SearchRepositoryImpl(get(), get()) }
}
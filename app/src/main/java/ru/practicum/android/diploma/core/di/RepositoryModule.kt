package ru.practicum.android.diploma.core.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.DetailRepositoryImpl
import ru.practicum.android.diploma.data.FavouriteRepositoryImpl
import ru.practicum.android.diploma.data.SearchRepositoryImpl
import ru.practicum.android.diploma.data.SimilarRepositoryImpl
import ru.practicum.android.diploma.data.VacancyMapper
import ru.practicum.android.diploma.data.filter.DirectoryRepositoryImpl
import ru.practicum.android.diploma.data.filter.FilterRepositoryImpl
import ru.practicum.android.diploma.data.filter.FiltersMapper
import ru.practicum.android.diploma.domain.api.DetailRepository
import ru.practicum.android.diploma.domain.api.DirectoryRepository
import ru.practicum.android.diploma.domain.api.SearchRepository
import ru.practicum.android.diploma.domain.favorite.FavouriteRepository
import ru.practicum.android.diploma.domain.models.filter.FilterRepository
import ru.practicum.android.diploma.domain.similar.SimilarRepository

val repositoryModule = module {
    single<SearchRepository> { SearchRepositoryImpl(get(), get(), get()) }
    single<SimilarRepository> { SimilarRepositoryImpl(get(), get(), get()) }
    single<DetailRepository> { DetailRepositoryImpl(get(), get(), get()) }
    single<DirectoryRepository> { DirectoryRepositoryImpl(get(), get(), get()) }
    single<FilterRepository> { FilterRepositoryImpl(get()) }
    single<FavouriteRepository> { FavouriteRepositoryImpl(get(), get()) }
    single { VacancyMapper() }
    single { FiltersMapper() }
}
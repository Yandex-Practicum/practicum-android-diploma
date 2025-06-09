package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.db.impl.FavoriteRepositoryImpl
import ru.practicum.android.diploma.domain.db.FavoriteRepository

val repositoryModule = module {
    factory<FavoriteRepository> {
        FavoriteRepositoryImpl(get(), get())
    }
}

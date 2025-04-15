package ru.practicum.android.diploma.di

import androidx.room.Room
import org.koin.dsl.module
import ru.practicum.android.diploma.data.db.FavoriteVacanciesDatabase
import ru.practicum.android.diploma.data.repositories.RepositoryFavoriteVacanciesImpl
import ru.practicum.android.diploma.domain.repositories.RepositoryFavoriteVacancies

val dataModule = module {
    single {
        Room.databaseBuilder(get(), FavoriteVacanciesDatabase::class.java, "FavoriteVacanciesDatabase.db")
            .build()
    }

    single { get<FavoriteVacanciesDatabase>().getVacancyDao() }

    single<RepositoryFavoriteVacancies> {
        RepositoryFavoriteVacanciesImpl(get())
    }
}

package ru.practicum.android.diploma.favorites.vacancies.di

import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.favorites.vacancies.data.FavoritesDatabase
import ru.practicum.android.diploma.favorites.vacancies.data.FavoritesVacanciesRepositoryImpl
import ru.practicum.android.diploma.favorites.vacancies.data.db.dao.FavoriteVacancyDao
import ru.practicum.android.diploma.favorites.vacancies.domain.api.FavoritesVacanciesInteractor
import ru.practicum.android.diploma.favorites.vacancies.domain.api.FavoritesVacanciesRepository
import ru.practicum.android.diploma.favorites.vacancies.domain.impl.FavoritesVacanciesInteractorImpl
import ru.practicum.android.diploma.favorites.vacancies.presentation.viewmodel.FavoritesVacanciesViewModel

val favoritesDataModule = module {
    single<FavoritesDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            FavoritesDatabase::class.java,
            "favorites_db"
        ).fallbackToDestructiveMigration()
            .build()
    }
    single<FavoriteVacancyDao> { get<FavoritesDatabase>().favoriteVacancyDao() }
    single<FavoritesVacanciesRepository> { FavoritesVacanciesRepositoryImpl(get()) }
}

val favoritesDomainModule = module {
    single<FavoritesVacanciesInteractor> {
        FavoritesVacanciesInteractorImpl(get())
    }
}

val favoritesPresentationModule = module {
    viewModel { FavoritesVacanciesViewModel(get()) }
}

val favoritesVacanciesModules = listOf(
    favoritesDataModule,
    favoritesDomainModule,
    favoritesPresentationModule
)

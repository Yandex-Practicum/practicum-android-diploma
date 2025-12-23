package ru.practicum.android.diploma.favorites.vacancies.di

import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.favorites.vacancies.data.FavoritesDatabase
import ru.practicum.android.diploma.favorites.vacancies.data.db.dao.FavoriteVacancyDao
import ru.practicum.android.diploma.favorites.vacancies.data.repository.FavoritesRepositoryImpl
import ru.practicum.android.diploma.favorites.vacancies.presentation.viewmodel.FavoritesVacanciesViewModel

val databaseModule = module {
    single<FavoritesDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            FavoritesDatabase::class.java,
            "favorites_db"
        ).fallbackToDestructiveMigration()
            .build()
    }
    single<FavoriteVacancyDao> { get<FavoritesDatabase>().favoriteVacancyDao() }
}

 val repositoryModule = module {
    single { FavoritesRepositoryImpl(get()) }
 }

val viewModelModule = module {
    viewModel { FavoritesVacanciesViewModel() }
}

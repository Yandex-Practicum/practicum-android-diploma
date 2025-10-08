package ru.practicum.android.diploma.di

import androidx.room.Room
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.data.database.AppDatabase
import ru.practicum.android.diploma.data.database.converters.VacancyDbConverter
import ru.practicum.android.diploma.data.database.dao.VacancyDao
import ru.practicum.android.diploma.data.repository.FavoritesRepositoryImpl
import ru.practicum.android.diploma.domain.api.FavoritesInteractor
import ru.practicum.android.diploma.domain.api.FavoritesRepository
import ru.practicum.android.diploma.domain.impl.FavoritesInteractorImpl
import ru.practicum.android.diploma.ui.root.favorites.FavoritesViewModel

val favoritesModule = module {
    viewModel {
        FavoritesViewModel(get())
    }
    single<VacancyDao> {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()
            .getVacancyDao()
    }
    single<Gson> {
        Gson()
    }
    factory<FavoritesRepository> {
        FavoritesRepositoryImpl(get(), get())
    }
    factory<FavoritesInteractor> {
        FavoritesInteractorImpl(get())
    }
    factory<VacancyDbConverter> {
        VacancyDbConverter(get())
    }
}

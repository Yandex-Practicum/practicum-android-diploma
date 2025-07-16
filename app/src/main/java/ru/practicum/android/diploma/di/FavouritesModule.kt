package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.favorites.data.FavoriteVacancyRepositoryImpl
import ru.practicum.android.diploma.favorites.domain.FavoriteVacancyInteractor
import ru.practicum.android.diploma.favorites.domain.FavoriteVacancyInteractorImpl
import ru.practicum.android.diploma.favorites.domain.FavoriteVacancyRepository
import ru.practicum.android.diploma.favorites.ui.viewmodel.FavoriteVacancyViewModel

val favouritesModule = module {

    single<FavoriteVacancyRepository> {
        FavoriteVacancyRepositoryImpl(get())
    }

    single<FavoriteVacancyInteractor> {
        FavoriteVacancyInteractorImpl(repository = get())
    }

    viewModel {
        FavoriteVacancyViewModel(interactor = get())
    }
}

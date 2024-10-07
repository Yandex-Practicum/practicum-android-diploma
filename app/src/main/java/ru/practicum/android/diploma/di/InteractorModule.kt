package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.favorite.domain.api.FavoriteVacancyInteractor
import ru.practicum.android.diploma.favorite.domain.impl.FavoriteVacancyInteractorImpl
import ru.practicum.android.diploma.search.domain.api.SearchVacancyInteractor
import ru.practicum.android.diploma.search.domain.impl.SearchVacancyInteractorImpl

val interactorModule = module {

    single<SearchVacancyInteractor> {
        SearchVacancyInteractorImpl(get())
    }

    single<FavoriteVacancyInteractor> {
        FavoriteVacancyInteractorImpl(get())
    }

}

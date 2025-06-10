package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.db.FavoriteInteractor
import ru.practicum.android.diploma.domain.impl.FavoriteInteractorImpl
import ru.practicum.android.diploma.domain.impl.VacanciesInteractorImpl
import ru.practicum.android.diploma.domain.vacancy.VacanciesInteractor

val interactorModule = module {
    factory<FavoriteInteractor> {
        FavoriteInteractorImpl(get())
    }

    factory<VacanciesInteractor> {
        VacanciesInteractorImpl(get())
    }
}

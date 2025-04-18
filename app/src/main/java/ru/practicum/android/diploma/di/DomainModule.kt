package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.api.InteractorFavoriteVacancies
import ru.practicum.android.diploma.domain.impl.InteractorFavoriteVacanciesImpl
import ru.practicum.android.diploma.domain.impl.SearchVacancyInteractorImpl
import ru.practicum.android.diploma.domain.interactor.SearchVacancyInteractor

val domainModule = module {
    single<InteractorFavoriteVacancies> {
        InteractorFavoriteVacanciesImpl(get())
    }

    single<SearchVacancyInteractor> {
        SearchVacancyInteractorImpl(get())
    }
}

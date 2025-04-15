package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.api.InteractorFavoriteVacancies
import ru.practicum.android.diploma.domain.impl.InteractorFavoriteVacanciesImpl

val domainModule = module {
    single<InteractorFavoriteVacancies> {
        InteractorFavoriteVacanciesImpl(get())
    }
}

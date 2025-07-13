package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.models.api.VacanciesInteractor
import ru.practicum.android.diploma.domain.sharing.SharingInteractor
import ru.practicum.android.diploma.domain.sharing.impl.SharingInteractorImpl
import ru.practicum.android.diploma.domain.vacancysearchscreen.impl.VacanciesInteractorImpl

val interactorModule = module {
    single<VacanciesInteractor> {
        VacanciesInteractorImpl(get())
    }

    single<SharingInteractor> {
        SharingInteractorImpl(get())
    }
}

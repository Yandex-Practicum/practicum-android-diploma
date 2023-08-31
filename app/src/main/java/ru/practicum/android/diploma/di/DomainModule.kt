package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.features.vacancydetails.domain.SharingInteractor
import ru.practicum.android.diploma.features.vacancydetails.domain.SharingInteractorImpl

val domainModule = module {

    factory<SharingInteractor> {
        SharingInteractorImpl()
    }

}
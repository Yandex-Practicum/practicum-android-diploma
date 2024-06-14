package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.VacancyDetailsRepositoryImpl
import ru.practicum.android.diploma.domain.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.api.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.impl.VacancyDetailsInteractorImpl

val vacancyModule = module {
    single<VacancyDetailsRepository> { VacancyDetailsRepositoryImpl(get(), get(), get()) }
    factory<VacancyDetailsInteractor> { VacancyDetailsInteractorImpl(get()) }
}

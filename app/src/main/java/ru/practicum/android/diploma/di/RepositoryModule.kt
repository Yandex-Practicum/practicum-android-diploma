package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.repository.VacancyRepositoryImpl
import ru.practicum.android.diploma.domain.repository.VacancyRepository

val repositoryModule = module {
    single<VacancyRepository> { VacancyRepositoryImpl(apiService = get()) }
}

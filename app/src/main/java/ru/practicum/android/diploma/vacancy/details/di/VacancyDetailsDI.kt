package ru.practicum.android.diploma.vacancy.details.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.favorites.vacancies.domain.api.FavoritesVacanciesInteractor
import ru.practicum.android.diploma.vacancy.details.data.VacancyDetailsRepositoryImpl
import ru.practicum.android.diploma.vacancy.details.domain.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.vacancy.details.domain.api.VacancyDetailsRepository
import ru.practicum.android.diploma.vacancy.details.domain.impl.VacancyDetailsInteractorImpl
import ru.practicum.android.diploma.vacancy.details.presentation.viewmodel.VacancyDetailsViewModel

val vacancyDetailsDomainModule = module {

    single<VacancyDetailsRepository> {
        VacancyDetailsRepositoryImpl()
    }

    single<VacancyDetailsInteractor> {
        VacancyDetailsInteractorImpl(get())
    }
}

val vacancyDetailsPresentationModule = module {
    viewModel { VacancyDetailsViewModel(get(), get<FavoritesVacanciesInteractor>()) }
}

val vacancyDetailsModules = listOf(
    vacancyDetailsDomainModule,
    vacancyDetailsPresentationModule
)

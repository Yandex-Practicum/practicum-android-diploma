package ru.practicum.android.diploma.vacancy.details.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.vacancy.details.domain.impl.VacancyInteractorImpl
import ru.practicum.android.diploma.vacancy.details.domain.interactor.VacancyInteractor
import ru.practicum.android.diploma.vacancy.details.domain.repository.VacancyDetailsRepository
import ru.practicum.android.diploma.vacancy.details.domain.repository.VacancyDetailsRepositoryImpl
import ru.practicum.android.diploma.vacancy.details.presentation.viewmodel.VacancyDetailsViewModel

val vacancyDetailViewModule = module {
    single<VacancyDetailsRepository> { VacancyDetailsRepositoryImpl() }
    single<VacancyInteractor> { VacancyInteractorImpl(get()) }
    viewModel { VacancyDetailsViewModel(get()) }
}

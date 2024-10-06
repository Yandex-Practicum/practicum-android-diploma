package ru.practicum.android.diploma.vacancy.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.vacancy.data.repositoryimpl.ExternalNavigator
import ru.practicum.android.diploma.vacancy.data.repositoryimpl.VacancyDetailRepositoryImpl
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyDetailRepository
import ru.practicum.android.diploma.vacancy.domain.usecase.VacancyDetailInteractor
import ru.practicum.android.diploma.vacancy.domain.usecase.impl.VacancyDetailInteractorImpl
import ru.practicum.android.diploma.vacancy.presentation.viewmodel.VacancyDetailViewModel

val vacancyDetailModule = module {

    viewModel {
        VacancyDetailViewModel(get())
    }

    single<VacancyDetailInteractor> {
        VacancyDetailInteractorImpl(get())
    }

    single<VacancyDetailRepository> {
        VacancyDetailRepositoryImpl(androidContext(), get(), get(), get())
    }
    single { ExternalNavigator(androidContext()) }
}

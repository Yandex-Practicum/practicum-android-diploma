package ru.practicum.android.diploma.vacancy.di

import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.vacancy.data.repositoryimpl.VacancyDetailRepositoryImpl
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyDetailRepository
import ru.practicum.android.diploma.vacancy.domain.usecase.VacancyDetailInteractor
import ru.practicum.android.diploma.vacancy.domain.usecase.impl.VacancyDetailInteractorImpl
import ru.practicum.android.diploma.vacancy.presentation.viewmodel.VacancyDetailViewModel

val vacancyDetailModule = module {
    single<VacancyDetailRepository> {
        VacancyDetailRepositoryImpl(get(), get(), androidContext())
    }

    single<VacancyDetailInteractor> {
        VacancyDetailInteractorImpl(get())
    }

    viewModel { (vacancyId: String) ->
        VacancyDetailViewModel(vacancyId, get())
    }
}

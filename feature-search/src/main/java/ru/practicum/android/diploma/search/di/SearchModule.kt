package ru.practicum.android.diploma.search.di

import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.search.data.repositoryimpl.network.VacanciesRepositoryImpl
import ru.practicum.android.diploma.search.data.repositoryimpl.sp.SearchRepositorySpImpl
import ru.practicum.android.diploma.search.domain.repository.SearchRepositorySp
import ru.practicum.android.diploma.search.domain.repository.VacanciesRepository
import ru.practicum.android.diploma.search.domain.usecase.VacanciesInteractor
import ru.practicum.android.diploma.search.domain.usecase.impl.VacanciesInteractorImpl
import ru.practicum.android.diploma.search.presentation.viewmodel.VacancyListViewModel
import ru.practicum.android.diploma.search.util.AreaConverter
import ru.practicum.android.diploma.search.util.IndustryConverter
import ru.practicum.android.diploma.search.util.VacancyConverter

val searchModule = module {
    single {
        AreaConverter()
    }

    single {
        IndustryConverter()
    }

    single {
        VacancyConverter()
    }

    single<VacanciesInteractor> {
        VacanciesInteractorImpl(get(), get())
    }

    single<SearchRepositorySp> {
        SearchRepositorySpImpl(get())
    }

    single<VacanciesRepository> {
        VacanciesRepositoryImpl(get(), get(), get(), get(), androidContext())
    }

    viewModel {
        VacancyListViewModel(get(), androidApplication())
    }
}

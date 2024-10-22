package ru.practicum.android.diploma.search.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
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
    viewModelOf(::VacancyListViewModel)
    factory {
        AreaConverter()
    }

    factory {
        IndustryConverter()
    }

    factory {
        VacancyConverter()
    }

    factory<VacanciesInteractor> {
        VacanciesInteractorImpl(get(), get())
    }

    factory<SearchRepositorySp> {
        SearchRepositorySpImpl(get())
    }

    factory<VacanciesRepository> {
        VacanciesRepositoryImpl(get(), get(), get(), get(), androidContext())
    }

}

package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.search.data.repository.FiltersRepositoryImpl
import ru.practicum.android.diploma.search.domain.api.FiltersInteractor
import ru.practicum.android.diploma.search.domain.api.FiltersRepository
import ru.practicum.android.diploma.search.domain.interactor.FiltersInteractorImpl
import ru.practicum.android.diploma.search.presenter.filter.ui.fields.viewmodel.FieldsViewModel
import ru.practicum.android.diploma.search.presenter.filter.FiltersViewModel

val filterModule = module {

    single<FiltersRepository> {
        FiltersRepositoryImpl(retrofitClient = get(), internetConnectionChecker = get())
    }

    factory<FiltersInteractor> {
        FiltersInteractorImpl(filtersRepository = get())
    }

    viewModel {
        FieldsViewModel(filtersInteractor = get())
    }

    viewModel {
        FiltersViewModel()
    }
}

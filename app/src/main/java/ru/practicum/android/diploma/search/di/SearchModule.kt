package ru.practicum.android.diploma.search.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.core.domain.models.Vacancy
import ru.practicum.android.diploma.core.ui.preview.mockList
import ru.practicum.android.diploma.search.data.SearchRepositoryImpl
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.search.domain.impl.SearchInteractorImpl
import ru.practicum.android.diploma.search.ui.SearchViewModel
import ru.practicum.android.diploma.search.ui.SearchViewState
import ru.practicum.android.diploma.search.ui.mock.SearchViewModelMock

val searchModule = module {
    viewModel<SearchViewModel> {
        // SearchViewModelImpl() //TODO temporary mock
        // пока не реализуем логику SearchViewModelImpl
        SearchViewModelMock(mockState = SearchViewState.Data(Vacancy.mockList()))
    }

    viewModel {
        SearchViewModelImpl(get())
    }

    single<SearchRepository> {
        SearchRepositoryImpl(get(), get())
    }

    single<SearchInteractor> {
        SearchInteractorImpl(get())
    }
}

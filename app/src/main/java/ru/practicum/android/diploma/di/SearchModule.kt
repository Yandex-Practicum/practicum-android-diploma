package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.search.data.SearchRepositoryImpl
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.search.domain.impl.SearchInteractorImpl
import ru.practicum.android.diploma.search.presentation.SearchViewModel

val searchModule = module {

    single<SearchRepository> { SearchRepositoryImpl(get()) }

    single<SearchInteractor> { SearchInteractorImpl(get()) }

    viewModel { SearchViewModel(get()) }
}


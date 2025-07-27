package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.data.network.RetrofitClient
import ru.practicum.android.diploma.search.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.search.data.repository.SearchRepositoryImpl
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.search.domain.interactor.SearchInteractorImpl
import ru.practicum.android.diploma.search.presenter.search.SearchViewModel
import ru.practicum.android.diploma.util.InternetConnectionChecker

val searchModule = module {
    single<NetworkClient> {
        RetrofitNetworkClient(get(), get())
    }

    single {
        RetrofitClient
    }

    single<InternetConnectionChecker> {
        InternetConnectionChecker(androidApplication())
    }

    single<SearchRepository> {
        SearchRepositoryImpl(get())
    }

    factory<SearchInteractor> {
        SearchInteractorImpl(get())
    }

    viewModel {
        SearchViewModel(get(), get())
    }
}

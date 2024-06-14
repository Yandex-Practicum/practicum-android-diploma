package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.data.SearchRepositoryImpl
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.domain.api.SearchInteractor
import ru.practicum.android.diploma.domain.api.SearchRepository
import ru.practicum.android.diploma.domain.impl.SearchInteractorImpl
import ru.practicum.android.diploma.ui.search.SearchViewModel

val searchModule = module {
    single<NetworkClient> { RetrofitNetworkClient(get(), androidContext()) }
    single<SearchInteractor> { SearchInteractorImpl(get()) }
    single<SearchRepository> { SearchRepositoryImpl(get(), get()) }
    viewModel {
        SearchViewModel(get(), get())
    }
}

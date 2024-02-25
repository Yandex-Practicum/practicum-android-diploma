package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.Constant
import ru.practicum.android.diploma.data.search.impl.SearchRepositoryImpl
import ru.practicum.android.diploma.data.search.network.HhApi
import ru.practicum.android.diploma.data.search.network.NetworkClient
import ru.practicum.android.diploma.data.search.network.RetrofitNetworkClient
import ru.practicum.android.diploma.domain.search.SearchInteractor
import ru.practicum.android.diploma.domain.search.SearchRepository
import ru.practicum.android.diploma.domain.search.impl.SearchInteractorImpl
import ru.practicum.android.diploma.ui.search.SearchViewModel
import ru.practicum.android.diploma.ui.search.adapter.SearchPage

val SearchModules = module {
    single<HhApi> {
        Retrofit.Builder()
            .baseUrl(Constant.HH_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HhApi::class.java)
    }

    single<SearchRepository> {
        SearchRepositoryImpl(get())
    }
    single<NetworkClient> {
        RetrofitNetworkClient(get(), get())
    }

    factory<SearchInteractor> {
        SearchInteractorImpl(get())
    }

    viewModel {
        SearchViewModel(get())
    }
}

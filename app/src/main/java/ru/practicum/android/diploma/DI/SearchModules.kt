package ru.practicum.android.diploma.DI


import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import ru.practicum.android.diploma.data.SearchRepositoryImpl
import ru.practicum.android.diploma.data.network.HhApi
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.domain.api.SearchInteractor
import ru.practicum.android.diploma.domain.api.SearchRepository
import ru.practicum.android.diploma.domain.impl.SearchInteractorImpl
import ru.practicum.android.diploma.ui.search.SearchViewModel

val SearchModules = module {
    single<HhApi> {
        Retrofit.Builder()
            .baseUrl(RetrofitNetworkClient.HH_BASE_URL)
            .build()
            .create(HhApi::class.java)
    }

    single<SearchRepository> {
        SearchRepositoryImpl(networkClient = get())
    }
    single<NetworkClient> {
        RetrofitNetworkClient(service = get())
    }

    factory<SearchInteractor> {
        SearchInteractorImpl(repository = get())
    }

    viewModel {
        SearchViewModel(searchInteractor = get())
    }
}

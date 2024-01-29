package ru.practicum.android.diploma.DI


import com.google.gson.Gson
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.SearchRepositoryImpl
import ru.practicum.android.diploma.data.network.HhApi
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.domain.SearchInteractor
import ru.practicum.android.diploma.domain.SearchRepository
import ru.practicum.android.diploma.domain.impl.SearchInteractorImpl
import ru.practicum.android.diploma.ui.search.SearchViewModel

val SearchModules = module {
    single<HhApi> {
        Retrofit.Builder()
            .baseUrl(RetrofitNetworkClient.HH_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HhApi::class.java)
    }
    factory {
        Gson()
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

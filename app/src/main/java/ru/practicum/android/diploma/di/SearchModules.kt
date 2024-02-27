package ru.practicum.android.diploma.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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

val SearchModules = module {
    single<HhApi> {
        Retrofit.Builder()
            .baseUrl(Constant.HH_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .apply {
                        addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    }
                    .build()
            )
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

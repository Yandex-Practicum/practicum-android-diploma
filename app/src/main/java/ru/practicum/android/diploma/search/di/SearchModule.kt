package ru.practicum.android.diploma.search.di

import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.search.data.SearchRepositoryImpl
import ru.practicum.android.diploma.search.data.mapper.DtoMapper
import ru.practicum.android.diploma.search.data.mapper.FilterMapper
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.data.network.NetworkClientImpl
import ru.practicum.android.diploma.search.data.network.SearchApi
import ru.practicum.android.diploma.search.data.network.provideRetrofit
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.search.domain.impl.SearchInteractorImpl
import ru.practicum.android.diploma.search.presentation.viewmodel.SearchFiltersViewModel
import ru.practicum.android.diploma.search.presentation.viewmodel.SearchIndustryFilterViewModel
import ru.practicum.android.diploma.search.presentation.viewmodel.SearchViewModel
import ru.practicum.android.diploma.search.utils.NetworkConnectionChecker

val utilsModule = module {
    single { NetworkConnectionChecker(get()) }
    single { DtoMapper() }
    single { FilterMapper() }
}

val networkModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }
    single { get<Retrofit>().create(SearchApi::class.java) }
    single<NetworkClient> { NetworkClientImpl(get(), get(), get()) }
}

val repositoryModule = module {
    single<SearchRepository> { SearchRepositoryImpl(get()) }
}

val interactorModule = module {
    single<SearchInteractor> { SearchInteractorImpl(get(), get()) }
}

val viewModelModule = module {
    viewModel { SearchViewModel(get()) }
    viewModel { SearchFiltersViewModel(get()) }
    viewModel { SearchIndustryFilterViewModel(get()) }
}

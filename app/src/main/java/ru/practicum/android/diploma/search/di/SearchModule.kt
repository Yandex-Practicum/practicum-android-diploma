package ru.practicum.android.diploma.search.di

import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.search.data.mapper.DtoMapper
import ru.practicum.android.diploma.search.data.mapper.FilterMapper
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.data.network.NetworkClientImpl
import ru.practicum.android.diploma.search.presentation.viewmodel.SearchFiltersViewModel
import ru.practicum.android.diploma.search.presentation.viewmodel.SearchIndustryFilterViewModel
import ru.practicum.android.diploma.search.presentation.viewmodel.SearchViewModel
import ru.practicum.android.diploma.search.utils.NetworkConnectionChecker

// utils и mapper
val utilsModule = module {
    single { NetworkConnectionChecker(get()) }
    single { DtoMapper() }
    single { FilterMapper() }
}

// network
val networkModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }
//    single { provideVacancyApi(get()) }
    single<NetworkClient> { NetworkClientImpl(get(), get(), get()) }
}

// repository
// val repositoryModule = module {
//    single<VacancyRepository> { VacancyRepositoryImpl(get()) }
// }

// interactor
// val interactorModule = module {
//    single<VacancyInteractor> { VacancyInteractorImpl(get(), get()) }
// }

fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder().build()
}

fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://practicum-diploma-8bc38133faba.herokuapp.com/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

// fun provideVacancyApi(retrofit: Retrofit): VacancyApi {
//    return retrofit.create(VacancyApi::class.java)
// }

val viewModelModule = module {
    viewModel { SearchViewModel() }
    viewModel { SearchFiltersViewModel() }
    viewModel { SearchIndustryFilterViewModel() }
}

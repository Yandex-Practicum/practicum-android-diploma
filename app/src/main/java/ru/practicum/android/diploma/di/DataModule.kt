package ru.practicum.android.diploma.di

import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.features.search.data.SearchVacancyRepositoryImplNetwork
import ru.practicum.android.diploma.features.search.domain.repository.SearchVacancyRepository
import ru.practicum.android.diploma.features.vacancydetails.data.models.VacancyDetailsMapper
import ru.practicum.android.diploma.features.vacancydetails.presentation.models.VacancyDetailsUiMapper
import ru.practicum.android.diploma.root.data.VacancyRepositoryImpl
import ru.practicum.android.diploma.root.data.network.HeadHunterApi
import ru.practicum.android.diploma.root.data.network.HeaderInterceptor
import ru.practicum.android.diploma.root.data.network.NetworkSearch
import ru.practicum.android.diploma.root.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.root.domain.VacancyRepository

val dataModule = module {

    single<HeaderInterceptor> {
        HeaderInterceptor()
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(get<HeaderInterceptor>())
            .build()
    }

    single<HeadHunterApi> {
        Retrofit.Builder()
            .baseUrl("https://api.hh.ru")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get<OkHttpClient>())
            .build()
            .create(HeadHunterApi::class.java)
    }

    single<NetworkSearch> {
        RetrofitNetworkClient(api = get(), context = get())
    }

    single<Gson> {
        Gson()
    }

    single<VacancyDetailsMapper>  {
        VacancyDetailsMapper()
    }

    singleOf(::SearchVacancyRepositoryImplNetwork).bind<SearchVacancyRepository>()

    single<VacancyDetailsUiMapper> {
        VacancyDetailsUiMapper()
    }

    single<VacancyRepository> {
        VacancyRepositoryImpl(detailsMapper = get(), networkClient = get(), gson = get())
    }
}
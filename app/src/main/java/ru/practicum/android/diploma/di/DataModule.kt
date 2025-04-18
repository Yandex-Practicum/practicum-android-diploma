package ru.practicum.android.diploma.di

import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import ru.practicum.android.diploma.data.db.FavoriteVacanciesDatabase
import ru.practicum.android.diploma.data.impl.RepositoryFavoriteVacanciesImpl
import ru.practicum.android.diploma.data.impl.SearchVacancyRepoImpl
import ru.practicum.android.diploma.data.network.api.HeadHunterApi
import ru.practicum.android.diploma.data.network.api.NetworkClient
import ru.practicum.android.diploma.data.network.api.RetrofitNetworkClient
import ru.practicum.android.diploma.data.network.token.AccessTokenProvider
import ru.practicum.android.diploma.data.network.token.PrefsAccessTokenProvider
import ru.practicum.android.diploma.data.network.token.TokenInterceptor
import ru.practicum.android.diploma.data.storage.AppPrefsService
import ru.practicum.android.diploma.data.utils.StringProvider
import ru.practicum.android.diploma.data.utils.StringProviderImpl
import ru.practicum.android.diploma.domain.impl.utils.NetworkCheckerImpl
import ru.practicum.android.diploma.domain.repositories.RepositoryFavoriteVacancies
import ru.practicum.android.diploma.domain.repositories.SearchVacancyRepository

val dataModule = module {
    single {
        Room.databaseBuilder(get(), FavoriteVacanciesDatabase::class.java, "FavoriteVacanciesDatabase.db").build()
    }

    single<StringProvider> {
        StringProviderImpl(androidContext())
    }

    single<NetworkClient> {
        RetrofitNetworkClient(
            api = get(),
            networkChecker = NetworkCheckerImpl(androidContext())
        )
    }

    single { AppPrefsService(get()) }

    single { PrefsAccessTokenProvider(get()) } bind AccessTokenProvider::class

    single { TokenInterceptor(get()) }

    single {
        OkHttpClient.Builder().addInterceptor(get<TokenInterceptor>()).build()
    }

    single<Json> {
        Json { ignoreUnknownKeys = true }
    }

    single<HeadHunterApi> {
        val json: Json = get()

        Retrofit.Builder().baseUrl("https://api.hh.ru/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType())).build()
            .create(HeadHunterApi::class.java)
    }

    single { get<FavoriteVacanciesDatabase>().getVacancyDao() }

    single<RepositoryFavoriteVacancies> {
        RepositoryFavoriteVacanciesImpl(get())
    }

    single<SearchVacancyRepository> {
        SearchVacancyRepoImpl(get(), get())
    }
}

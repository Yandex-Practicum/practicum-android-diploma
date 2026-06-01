package ru.practicum.android.diploma.core.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.core.data.database.AppDatabase
import ru.practicum.android.diploma.core.data.formatters.CurrencyFormatter
import ru.practicum.android.diploma.core.data.network.AuthInterceptor
import ru.practicum.android.diploma.core.data.network.NetworkClient
import ru.practicum.android.diploma.core.data.network.NetworkClientImpl
import ru.practicum.android.diploma.core.data.repository.ConnectivityRepositoryImpl
import ru.practicum.android.diploma.core.domain.repository.ConnectivityRepository

private const val BASE_URL = "https://android-diploma.education-services.ru/"
private const val DB_NAME = "diploma.db"

val coreModule = module {

    single {
        val builder = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(BuildConfig.API_ACCESS_TOKEN))
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
        }
        builder.build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<ConnectivityManager> {
        val context: Context = get()
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    single<ConnectivityRepository> {
        ConnectivityRepositoryImpl(get())
    }

    single<CurrencyFormatter> {
        CurrencyFormatter(get())
    }

    single<NetworkClient> {
        NetworkClientImpl(get(), get())
    }

    single<AppDatabase> {
        Room.databaseBuilder(get(), AppDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }
}

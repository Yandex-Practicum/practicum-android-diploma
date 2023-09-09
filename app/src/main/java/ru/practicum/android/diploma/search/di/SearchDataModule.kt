package ru.practicum.android.diploma.search.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.di.annotations.AppEmail
import ru.practicum.android.diploma.di.annotations.ApplicationScope
import ru.practicum.android.diploma.di.annotations.BaseUrl
import ru.practicum.android.diploma.search.data.network.AlternativeRemoteDataSource
import ru.practicum.android.diploma.search.data.network.ApiHelper
import ru.practicum.android.diploma.search.data.network.HhApiService
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.data.network.RetrofitClient
import ru.practicum.android.diploma.search.data.network.cache.CacheInterceptor
import ru.practicum.android.diploma.search.data.network.cache.ForceCacheInterceptor
import java.io.File
import javax.inject.Named


@Module
class SearchDataModule {
    @ApplicationScope
    @Provides
    fun createApiService(retrofit: Retrofit): HhApiService {
        return retrofit.create(HhApiService::class.java)
    }
    
    @Provides
    fun bindNetworkClient(retrofitClient: RetrofitClient): NetworkClient{
        return retrofitClient
    }

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
        @BaseUrl baseUrl: String
    ): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideConverterFactory(): Converter.Factory {
        val contentType = "application/json".toMediaType()
        return Json{ignoreUnknownKeys = true}.asConverterFactory(contentType)
    }

    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        context: Context,
        @Named("authorization_key")
        authInterceptor: Interceptor,
        cacheInterceptor: CacheInterceptor,
        forceCacheInterceptor: ForceCacheInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(Cache(File(context.cacheDir, "http-cache"), 10L * 1024L * 1024L))
            .addNetworkInterceptor(cacheInterceptor)
            .addInterceptor(forceCacheInterceptor)
            .addInterceptor(authInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    
    @Named("authorization_key")
    @Provides
    fun providesAuthInterceptor(context: Context,
                                @AppEmail appEmail: String): Interceptor {
        return Interceptor { chain ->
            val request = chain
                .request()
                .newBuilder()
                .addHeader("Authorization", "Bearer ${BuildConfig.HH_ACCESS_TOKEN}")
                .addHeader("HH-User-Agent", "${context.getString(R.string.app_name)} ($appEmail)")
                .build()
            
            chain.proceed(request)
        }
    }

    @Provides
    fun provideAlternativeRemoteDataSource(apiHelper: ApiHelper): AlternativeRemoteDataSource =
        apiHelper
}



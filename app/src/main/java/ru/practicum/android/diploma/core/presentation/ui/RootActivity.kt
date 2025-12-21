package ru.practicum.android.diploma.core.presentation.ui

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.search.data.mapper.Mapper
import ru.practicum.android.diploma.search.data.network.AuthInterceptor
import ru.practicum.android.diploma.search.data.network.NetworkClientImpl
import ru.practicum.android.diploma.search.data.network.VacancyApi
import ru.practicum.android.diploma.search.data.network.VacancyRepositoryImpl
import ru.practicum.android.diploma.search.domain.impl.VacancyInteractorImpl
import ru.practicum.android.diploma.search.domain.model.Result
import ru.practicum.android.diploma.search.utils.NetworkConnectionChecker

class RootActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        val button = findViewById<Button>(R.id.button)
        // Пример использования access token для HeadHunter API
        networkRequestExample(accessToken = BuildConfig.API_ACCESS_TOKEN)
        val interactor = VacancyInteractorImpl(
            VacancyRepositoryImpl(
                NetworkClientImpl(
                    Retrofit.Builder()
                        .client(
                            OkHttpClient.Builder().addInterceptor(AuthInterceptor { BuildConfig.API_ACCESS_TOKEN })
                                .build()
                        ).baseUrl("https://practicum-diploma-8bc38133faba.herokuapp.com/")
                        .addConverterFactory(
                            GsonConverterFactory.create()
                        ).build().create(VacancyApi::class.java), NetworkConnectionChecker(this)
                )
            ), Mapper()
        )
        button.setOnClickListener {
            lifecycleScope.launch {
                interactor.getVacancies().collect {
                    when (it) {
                        is Result.Success ->
                            Log.d("Taaaag", it.data.pages.toString())

                        is Result.Error -> Log.d("Taaaag", it.message + it.exception?.message)
                    }
                }
            }
        }
    }

    private fun networkRequestExample(accessToken: String) {
        // ...
    }

}

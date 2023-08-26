package ru.practicum.android.diploma.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.App
import ru.practicum.android.diploma.di.ViewModelFactory
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class RootActivity : AppCompatActivity() {
    private val component by lazy {
        (application as App).component
            .activityComponentFactory()
            .create()
    }

    @Inject
    lateinit var logger: Logger

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[RootViewModel::class.java]
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        logger.log(thisName, "onCreate() -> Unit $logger")
        // Пример использования access token для HeadHunter API
        networkRequestExample(accessToken = BuildConfig.HH_ACCESS_TOKEN)
        viewModel.doSmth("hello From Activity $viewModel")
    }

    private fun networkRequestExample(accessToken: String) {
        // ...
    }

}
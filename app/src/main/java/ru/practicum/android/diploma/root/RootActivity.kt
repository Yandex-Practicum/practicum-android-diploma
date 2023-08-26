package ru.practicum.android.diploma.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.App
import ru.practicum.android.diploma.util.thisName

class RootActivity : AppCompatActivity() {
    private val component by lazy {
        (application as App).component

    }

    private val logger: Logger by lazy {
        component.provideLogger()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        logger.log(thisName, "onCreate() -> Unit $logger")
        // Пример использования access token для HeadHunter API
        networkRequestExample(accessToken = BuildConfig.HH_ACCESS_TOKEN)
    }

    private fun networkRequestExample(accessToken: String) {
        // ...
    }

}
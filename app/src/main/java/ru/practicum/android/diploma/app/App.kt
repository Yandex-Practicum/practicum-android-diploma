package ru.practicum.android.diploma.app

import android.app.Application
import android.content.Context
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.di.components.AppComponent
import ru.practicum.android.diploma.di.components.DaggerAppComponent
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

private const val APP_PREFERENCES = "app_preferences"

class App: Application() {

    @Inject
    lateinit var logger: Logger
    val component by lazy(LazyThreadSafetyMode.NONE) {
        DaggerAppComponent.factory()
            .create(
                context = this,
                baseUrl = getString(R.string.base_url),
                prefsKey = APP_PREFERENCES,
                appEmail = getString(R.string.app_email)
            )
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
        logger.log(thisName, "onCreate()")
    }
}

val Context.appComponent: AppComponent
    get() = when(this){
        is App -> component
        else -> this.applicationContext.appComponent
    }
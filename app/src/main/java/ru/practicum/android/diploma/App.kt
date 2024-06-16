package ru.practicum.android.diploma

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.practicum.android.diploma.di.countryModule
import ru.practicum.android.diploma.di.dataModule
import ru.practicum.android.diploma.di.favoriteModule
import ru.practicum.android.diploma.di.filtrationModule
import ru.practicum.android.diploma.di.industryModule
import ru.practicum.android.diploma.di.regionModule
import ru.practicum.android.diploma.di.searchModule
import ru.practicum.android.diploma.di.sharingModule
import ru.practicum.android.diploma.di.vacancyModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                dataModule,
                favoriteModule,
                filtrationModule,
                searchModule,
                sharingModule,
                vacancyModule,
                industryModule,
                countryModule,
                regionModule
            )
        }
    }
}

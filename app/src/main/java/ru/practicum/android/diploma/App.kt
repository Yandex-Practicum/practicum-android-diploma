package ru.practicum.android.diploma

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.practicum.android.diploma.area.di.areaModule
import ru.practicum.android.diploma.core.di.coreModule
import ru.practicum.android.diploma.country.di.countryModule
import ru.practicum.android.diploma.favorites.di.favoritesModule
import ru.practicum.android.diploma.filter.di.filterModule
import ru.practicum.android.diploma.industry.di.industryModule
import ru.practicum.android.diploma.region.di.regionModule
import ru.practicum.android.diploma.search.di.searchModule
import ru.practicum.android.diploma.team.di.teamModule
import ru.practicum.android.diploma.vacancy.di.vacancyModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                coreModule,
                searchModule,
                filterModule,
                vacancyModule,
                areaModule,
                countryModule,
                regionModule,
                industryModule,
                favoritesModule,
                teamModule
            )
        }
    }

}

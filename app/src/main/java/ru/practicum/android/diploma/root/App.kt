package ru.practicum.android.diploma.root

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.practicum.android.diploma.cache.di.cacheDataModule
import ru.practicum.android.diploma.data.db.di.dbDataModule
import ru.practicum.android.diploma.data.networkclient.di.networkDataModule
import ru.practicum.android.diploma.data.sp.di.spDataModule
import ru.practicum.android.diploma.favorites.di.favoritesModule
import ru.practicum.android.diploma.filter.filter.di.filterModule
import ru.practicum.android.diploma.filter.industry.di.industryModule
import ru.practicum.android.diploma.filter.place.di.placeModule
import ru.practicum.android.diploma.navigate.di.navigateModel
import ru.practicum.android.diploma.search.di.searchModule
import ru.practicum.android.diploma.vacancy.di.vacancyDetailModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                networkDataModule,
                spDataModule,
                dbDataModule,
                searchModule,
                filterModule,
                industryModule,
                placeModule,
                favoritesModule,
                vacancyDetailModule,
                navigateModel,
                cacheDataModule
            )
        }
    }
}

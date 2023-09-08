package ru.practicum.android.diploma.di.components

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.practicum.android.diploma.app.App
import ru.practicum.android.diploma.details.di.DetailsDataModule
import ru.practicum.android.diploma.details.di.DetailsDomainModule
import ru.practicum.android.diploma.di.annotations.AppEmail
import ru.practicum.android.diploma.di.annotations.ApplicationScope
import ru.practicum.android.diploma.di.annotations.BaseUrl
import ru.practicum.android.diploma.di.annotations.PrefsKey
import ru.practicum.android.diploma.favorite.di.FavoriteDomainModule
import ru.practicum.android.diploma.di.modules.LoggerModule
import ru.practicum.android.diploma.favorite.di.FavoritesDataModule
import ru.practicum.android.diploma.filter.di.FilterDataModule
import ru.practicum.android.diploma.filter.di.FilterDomainModule
import ru.practicum.android.diploma.search.di.SearchDataModule
import ru.practicum.android.diploma.search.di.SearchDomainModule
import ru.practicum.android.diploma.sharing.di.SharingModule

@ApplicationScope

@Component(
    modules = [
        LoggerModule::class,
        SearchDataModule::class,
        SearchDomainModule::class,
        DetailsDataModule::class,
        DetailsDomainModule::class,
        FavoriteDomainModule::class,
        FavoritesDataModule::class,
        FilterDomainModule::class,
        FilterDataModule::class,
        SharingModule::class
    ]
)

interface AppComponent {
    fun inject(app: App)
    fun activityComponentFactory(): ActivityComponent.Factory

    @Component.Factory
    interface AppComponentFactory {
        fun create(
            @BindsInstance context: Context,
            @BindsInstance @BaseUrl baseUrl: String,
            @BindsInstance @PrefsKey prefsKey: String,
            @BindsInstance @AppEmail appEmail: String
        ): AppComponent
    }
}
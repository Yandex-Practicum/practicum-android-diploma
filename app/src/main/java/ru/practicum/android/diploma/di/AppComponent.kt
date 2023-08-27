package ru.practicum.android.diploma.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.practicum.android.diploma.app.App
import ru.practicum.android.diploma.di.annotations.ApplicationScope
import ru.practicum.android.diploma.di.annotations.BaseUrl
import ru.practicum.android.diploma.di.modules.LoggerModule
import ru.practicum.android.diploma.search.di.NetworkDataModule
import ru.practicum.android.diploma.search.di.SearchDomainModule

@ApplicationScope
@Component(modules = [LoggerModule::class, NetworkDataModule::class, SearchDomainModule::class])
interface AppComponent {
    fun inject(app: App)
    fun activityComponentFactory(): ActivityComponent.Factory
    @Component.Factory
    interface AppComponentFactory {
        fun create(
            @BindsInstance context: Context,
            @BindsInstance @BaseUrl baseUrl: String
        ): AppComponent
    }
}
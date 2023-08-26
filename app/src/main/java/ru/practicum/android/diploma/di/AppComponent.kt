package ru.practicum.android.diploma.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.CoroutineScope
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.app.App
import ru.practicum.android.diploma.util.Debouncer
import javax.inject.Singleton

@ApplicationScope
@Component(modules = [LoggerModule::class])
interface AppComponent {
    fun inject(app: App)
    fun provideLogger(): Logger
    @Component.Factory
    interface AppComponentFactory{
      fun create(@BindsInstance context: Context): AppComponent
    }
}
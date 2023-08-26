package ru.practicum.android.diploma.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.app.App
import ru.practicum.android.diploma.root.RootActivity

@ApplicationScope
@Component(modules = [LoggerModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(app: App)
    fun inject(rootActivity: RootActivity)
    fun provideLogger(): Logger
    @Component.Factory
    interface AppComponentFactory{
      fun create(@BindsInstance context: Context): AppComponent
    }
}
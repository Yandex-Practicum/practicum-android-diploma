package ru.practicum.android.diploma.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.practicum.android.diploma.app.App
import ru.practicum.android.diploma.di.annotations.ApplicationScope
import ru.practicum.android.diploma.di.modules.LoggerModule

@ApplicationScope
@Component(modules = [LoggerModule::class])
interface AppComponent {
    fun inject(app: App)
    fun activityComponentFactory(): ActivityComponent.Factory
    @Component.Factory
    interface AppComponentFactory{
      fun create(@BindsInstance context: Context): AppComponent
    }
}
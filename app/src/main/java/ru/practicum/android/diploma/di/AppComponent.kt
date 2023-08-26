package ru.practicum.android.diploma.di

import dagger.Component
import ru.practicum.android.diploma.app.App

@Component(modules = [LoggerModule::class])
abstract class AppComponent {
    abstract fun inject(app: App)
}
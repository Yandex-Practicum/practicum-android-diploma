package ru.practicum.android.diploma.di.modules

import dagger.Binds
import dagger.Module
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.LoggerImpl
import ru.practicum.android.diploma.di.annotations.ApplicationScope

@Module
interface LoggerModule {
// нужно решить 1 логгер на все приложение или для каждого фрагмента свой
//    @ApplicationScope
    @Binds
    fun bindLogger(impl: LoggerImpl): Logger
}
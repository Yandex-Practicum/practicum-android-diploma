package ru.practicum.android.diploma.di

import dagger.Binds
import dagger.Module
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.LoggerImpl
import javax.inject.Singleton


@Module
interface LoggerModule {
    @Binds
    fun bindLogger(impl: LoggerImpl): Logger
}
package ru.practicum.android.diploma.di

import dagger.Binds
import dagger.Module
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.LoggerImpl

@Module
interface LoggerModule {
    @Binds
    fun bindLogger(impl: LoggerImpl): Logger
}
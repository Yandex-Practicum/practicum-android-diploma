package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.repository.DataRepository

val repositoryModule = module {
    single { DataRepository(apiService = get()) }
}

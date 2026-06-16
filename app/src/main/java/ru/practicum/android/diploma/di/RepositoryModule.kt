package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.SomeSource
import ru.practicum.android.diploma.domain.SomeRepository

val RepositoryModule = module {
    // Для проверки DI
    single { SomeSource() }
    factory { SomeRepository(get()) }
}

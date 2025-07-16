package ru.practicum.android.diploma.di

import kotlinx.coroutines.CoroutineScope
import org.koin.dsl.module
import ru.practicum.android.diploma.util.Debouncer

val appModule = module {
    factory { (scope: CoroutineScope) ->
        Debouncer(scope)
    }
}

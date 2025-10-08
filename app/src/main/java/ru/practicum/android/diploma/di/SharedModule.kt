package ru.practicum.android.diploma.di

import android.content.Intent
import org.koin.dsl.module
import ru.practicum.android.diploma.data.sharing.ExternalNavigator

val sharedModule = module {
    factory<ExternalNavigator> {
        ExternalNavigator(get(), get())
    }
    factory<Intent> {
        Intent()
    }
}

package ru.practicum.android.diploma.di

import com.google.gson.Gson
import org.koin.dsl.module

val domainModule = module {
    // заглушка (чтобы проект собрался), потом убрать
    factory { Gson() }
}

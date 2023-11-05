package ru.practicum.android.diploma.core.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.detail.DetailInteractor
import ru.practicum.android.diploma.domain.detail.impl.DetailInteractorImpl


val interactorModule = module {

    single<DetailInteractor> { DetailInteractorImpl(get()) }
}
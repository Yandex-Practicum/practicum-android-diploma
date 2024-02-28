package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.ui.favorites.viewmodel.FavoriteViewModel
import ru.practicum.android.diploma.ui.main.viewmodel.MainViewModel

val viewModelModule = module {

    single {
        MainViewModel(get())
    }

    single {
        FavoriteViewModel(get())
    }
}

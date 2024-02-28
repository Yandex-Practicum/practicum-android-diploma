package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.ui.favorites.viewmodel.FavoriteViewModel
import ru.practicum.android.diploma.ui.main.viewmodel.MainViewModel
import ru.practicum.android.diploma.ui.vacancydetail.viewmodel.DetailViewModel

val viewModelModule = module {

    single {
        MainViewModel(get())
    }

    single {
        DetailViewModel(get())
    }

    single {
        FavoriteViewModel(get())
    }
}

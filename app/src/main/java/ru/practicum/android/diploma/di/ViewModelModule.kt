package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.filters.FiltersRepository
import ru.practicum.android.diploma.data.filters.FiltersRepositoryImpl
import ru.practicum.android.diploma.ui.favorites.viewmodel.FavoriteViewModel
import ru.practicum.android.diploma.ui.main.viewmodel.MainViewModel
import ru.practicum.android.diploma.ui.vacancydetail.viewmodel.DetailViewModel
import ru.practicum.android.diploma.ui.workplace.WorkplaceViewModel

val viewModelModule = module {

    single<FiltersRepository> {
        FiltersRepositoryImpl(get())
    }

    single {
        MainViewModel(get(), get())
    }

    single {
        DetailViewModel(get(), get())
    }

    single {
        FavoriteViewModel(get())
    }
    single {
        WorkplaceViewModel()
    }
}

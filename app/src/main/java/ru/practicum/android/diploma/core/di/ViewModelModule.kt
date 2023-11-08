package ru.practicum.android.diploma.core.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.detail.DetailViewModel
import ru.practicum.android.diploma.presentation.favourite.FavouriteViewModel
import ru.practicum.android.diploma.presentation.filter.FilterViewModel
import ru.practicum.android.diploma.presentation.filter.chooseArea.ChooseAreaViewModel
import ru.practicum.android.diploma.presentation.filter.chooseArea.ChooseCountryViewModel
import ru.practicum.android.diploma.presentation.search.SearchViewModel
import ru.practicum.android.diploma.presentation.similar.SimilarViewModel


val viewModelModule = module {

    viewModelOf(::SearchViewModel)
    viewModel { DetailViewModel(get(), get(), get()) }
    viewModel{ SimilarViewModel(get(),get()) }
    viewModel { FilterViewModel(get()) }
    viewModel { ChooseAreaViewModel(get()) }
    viewModel { ChooseCountryViewModel(get(),get()) }
    viewModel { FavouriteViewModel(get()) }
}

package ru.practicum.android.diploma.filter.filter.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.filter.filter.data.repositoryimpl.sp.FilterSPRepositoryImpl
import ru.practicum.android.diploma.filter.filter.domain.repository.FilterSPRepository
import ru.practicum.android.diploma.filter.filter.domain.usecase.impl.FilterSPInteractor
import ru.practicum.android.diploma.filter.filter.domain.usecase.impl.FilterSPInteractorImpl
import ru.practicum.android.diploma.filter.filter.presentation.viewmodel.FilterViewModel

val filterModule = module {
    single<FilterSPRepository> {
        FilterSPRepositoryImpl(androidContext().getSharedPreferences("shared_prefs", Application.MODE_PRIVATE))
    }
    single<FilterSPInteractor> {
        FilterSPInteractorImpl(get())
    }
    viewModel {
        FilterViewModel(get())
    }
}

package ru.practicum.android.diploma.filter.filter.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.practicum.android.diploma.filter.filter.data.repositoryimpl.sp.FilterSPRepositoryImpl
import ru.practicum.android.diploma.filter.filter.domain.repository.FilterSPRepository
import ru.practicum.android.diploma.filter.filter.domain.usecase.FilterSPInteractor
import ru.practicum.android.diploma.filter.filter.domain.usecase.impl.FilterSPInteractorImpl
import ru.practicum.android.diploma.filter.filter.presentation.viewmodel.FilterViewModel

val filterModule = module {
    viewModelOf(::FilterViewModel)
    factory<FilterSPRepository> {
        FilterSPRepositoryImpl(get())
    }

    factory<FilterSPInteractor> {
        FilterSPInteractorImpl(get())
    }

}
